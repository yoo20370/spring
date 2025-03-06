package jpabook.jpashop.repository.order.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 패키지를 나눈 이유
// 핵심 비즈니스를 찾을 때는, orderRepository를 찾고
// query쪽은 화면이나 API에 의존관계가 있는 것을 분리하기 위함
// 엔티티가 아닌 특정 화면이나 API에 핏한 것은 별도로 분리
// 화면에 관련된 것들은 생각보다 쿼리랑 밀접하게 될 때가 많다.
// 서로 라이프싸이클이 다르므로 관심사 분리할 수 있다.

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    // 별도로 만든 이유
    // OrderQueryRepository에서 OrderQueryDto가 아니라 OrderApiController에 있는 OrderDto를 참조하면
    // 리포지토리가 컨트롤러를 참조하는 의존관계 순환이 발생
    // 또한 같은 패키지에 분리해서 사용하기 위함(용도에 명확) - (?)
    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = findOrders(); // query 1번 -> 2개의 주문

        result.forEach(o -> {
            // 쿼리를 따로 날려서 OrderItems를 가져와서 컬렉션을 채운다.
           List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId()); // 각 주문에 대하여 orderItem과 Item 조인 조회 -> Query 2번
           o.setOrderItems(orderItems);
        });

        return result;
    }

    // 쿼리를 한 번 날리고 메모리에서 map으로 가져온 뒤, 메모리에서 매칭해서 값을 설정
    // 쿼리가 총 두 번 나간다.
    public List<OrderQueryDto> findAllByDto_optimization1() {
        List<OrderQueryDto> result = findOrders(); // 쿼리 1번

        // 주문 결과를 돌면서, 주문 아이디 리스트로 변환 (아이디 추출)
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());

        // 쿼리 2번
        // 주문 아이디 리스트를 IN 쿼리 파라미터로 넣어서 한 번에 조회하도록 만든다.
        // 이대로 반환해도 됨
        List<OrderItemQueryDto> orderItems = em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id , i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi " +
                        " join oi.item i " +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        // map으로 바꿔서 성능 최적화
        // 키가 orderItemQueryDto.getOrderId(), 값은 OrderItemQueryDto
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));

        // order에 대해서 forEach를 돌리면서 Map에서 주문 아이디에 맞는 OrderItem을 찾아서 Order.orderItems에 넣어주는 것
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    // 쿼리를 한 번 날리고 메모리에서 map으로 가져온 뒤, 메모리에서 매칭해서 값을 설정
    // 쿼리가 총 두 번 나간다.
    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrders();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));

        // order에 대해서 forEach를 돌리면서 Map에서 주문 아이디에 맞는 OrderItem을 찾아서 Order.orderItems에 넣어주는 것
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        // 쿼리 2번
        // 주문 아이디 리스트를 IN 쿼리 파라미터로 넣어서 한 번에 조회하도록 만든다.
        // 이대로 반환해도 됨
        List<OrderItemQueryDto> orderItems = em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id , i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi " +
                        " join oi.item i " +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        // map으로 바꿔서 성능 최적화
        // 키가 orderItemQueryDto.getOrderId(), 값은 OrderItemQueryDto
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));
        return orderItemMap;
    }

    private static List<Long> toOrderIds(List<OrderQueryDto> result) {
        // 주문 결과를 돌면서, 주문 아이디 리스트로 변환 (아이디 추출)
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
        return orderIds;
    }


    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        // oi.order.id의 경우 외래키가 OrderItem에 있기 때문에 외래키를 그대로 사용(order를 참조하지 않음)
        return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id , i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi " +
                        " join oi.item i " +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                        // SQL에는 데이터 플랫하게 들어가야 함, 일대다의 컬렉션의 경우 데이터가 뻥튀기 되어 불가능
                        "select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name , o.orderDate, o.orderStatus, d.address) from Order o " +
                                " join o.member m " +
                                " join o.delivery d ", OrderQueryDto.class)
                .getResultList();
    }


    // Order랑 OrderItem와 OrderItem은 Item를 모두 다 조인해서 가져온다. 한 방 쿼리로 가져오는 것
    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.orderStatus, d.address, i.name, oi.orderPrice, oi.count) " +
                        " from Order o " +
                        " join o.member m " +
                        " join o.delivery d " +
                        " join o.orderItems oi " +
                        " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }
}
