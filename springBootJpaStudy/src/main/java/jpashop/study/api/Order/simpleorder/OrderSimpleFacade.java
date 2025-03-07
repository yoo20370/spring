package jpashop.study.api.Order.simpleorder;

import jpashop.study.api.Result;
import jpashop.study.domain.Order;
import jpashop.study.domain.OrderItem;
import jpashop.study.domain.OrderSearch;
import jpashop.study.repository.query.OrderSimpleRepository;
import jpashop.study.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderSimpleFacade {

    private final OrderSimpleRepository orderSimpleRepository;
    private final OrderService orderService;

    public List<Order> simpleOrdersV1() {
        return orderService.findAll();
    }

    public Result<List<OrderDto>> simpleOrdersV2() {
        // 문제점
        // N + 1 발생
        // 지연로딩을 수행했으므로, 엔티티를 초기화하지 않고 반환하면 null이 반환될 수 있다.
        // hibernate5Module 켠 후, 엔티티를 초기화 하지 않으면 null이 반환됨
        // hibernate5Mudule이 없으면, 프록시 객체가 초기화 되었든 아니든 프록시 객체를 JSON으로 변환할 수 없다.

        List<Order> orders = orderService.findAllByString(new OrderSearch());

        // 일반 조인을 사용하면 연관관계 엔티티 데이터를 가져오지 않는다.
        // 그러므로 지연로딩이 발생하여 N + 1 문제가 발생한다.
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress().getCity();
            for (OrderItem orderItem : order.getOrderItems()) {
                orderItem.getItem().getName();
            }
        }
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o))
                .collect(Collectors.toList());

        return new Result<List<OrderDto>>("주문 목록", collect);
    }

    public Result<List<OrderDto>> simpleOrdersV3() {

        // 패치 조인을 사용하여 모든 정보를 한 번에 가져온다.
        // 단, 일대다 컬렉션도 fetch join으로 가져왔으므로, 페이징이 불가능하며(order 로우가 뻥튀기 됨), 데이터 중복이 발생한다.
        List<Order> orders = orderSimpleRepository.findAll();
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o))
                .collect(Collectors.toList());

        return new Result<List<OrderDto>>("주문 목록", collect);
    }

    public Result<List<OrderDto>> simpleOrdersV4() {

        // JPQL에서 일반조인을 사용해서 필요한 데이터만 조회한 후, DTO에 담아서 반환
        return new Result<List<OrderDto>>("message", orderSimpleRepository.findOrderDtos());
    }
}
