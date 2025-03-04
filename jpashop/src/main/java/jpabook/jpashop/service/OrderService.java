package jpabook.jpashop.service;

import jpabook.jpashop.entity.*;
import jpabook.jpashop.entity.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member findMember = memberRepository.findOne(memberId);
        Item findItem = itemRepository.findOne(itemId);
        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(new Address(findMember.getAddress().getCity(), findMember.getAddress().getStreet(), findMember.getAddress().getZipcode()));

        // 주문상품 생성
        // 주문상품을 생성할 떄, createOrderItem()만 사용해야 한다. 그러므로 생성자의 접근 제어자를 protected로 해줘야 한다.
        // JPA는 기본 생성자 접근 제어자를 protected 까지 허용함 -> 리플렉션, 프록시 기술 객체 생성 가능
        OrderItem newOrderItem = OrderItem.createOrderItem(findItem, findItem.getPrice(), count);

        // 주문 생성
        Order createOrder = Order.creatOrder(findMember, delivery, newOrderItem);

        // 영속성 전이 해놨기 때문에 Delivery, OrderItem가 자동으로 영속화 됨
        // 영속성 전이는 보통, Order가 Delivery와 OrderItem을 관리할 때 정도로만 사용하면 됨 즉, Delivery와 OrderItem의 주인이 유일할 때만 사용
        // 즉, Delivery나 OrderItem을 단 하나의 Order만 참조하는 경우, 둘 이상의 Order가 이들을 참조한다면 문제 발생
        // (order1이 제거되어 둘 다 제거 됐는데, order2의 delivery와 orderItem이 사라짐)

        // 주문 저장
        orderRepository.save(createOrder);

        return createOrder.getId();
    }

    // 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order findOrder = orderRepository.findOne(orderId);

        // 주문 취소
        findOrder.cancel();

        // 일반적 데이터 SQL을 직접 다루는 라이브러리(마이바티스, JDBC 템플릿 등)
        // 내가 직접 데이터를 변경한 경우, 서비스 밖에서 내가 업데이트 쿼리를 직접 작성해서 날려야 한다.
        // 데이터를 변경하고, 그 데이터를 꺼내서 쿼리 파리미터에 넣어서 쿼리를 날여야 한다.
        // 그렇게 되면 서비스 계층에서 비즈니스 로직을 다 쓸 수 밖에 없음
        // JPA를 활용하면 데이터만 변경한 경우 JPA가 알아서 바뀐 변경 포인트는 감지해서 DB에 update 쿼리를 날린다.
    }
    // 검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }
}
