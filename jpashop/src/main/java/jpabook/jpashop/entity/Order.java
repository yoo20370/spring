package jpabook.jpashop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // member 필드가 연관관계 주인 즉, 두 필드 중 어떤 필드가 FK를 관리하는지
    // member 필드가 FK를 관리하고 member의 값이 변경되면 외래키의 값이 변경된다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();


    // 일대일 관계일 때, 외래키를 액세스 많이 하는 곳에 둔다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // @Temporal로 매핑해줄 필요가 없음, 하이버네이트가 알아서 처리해줌
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태 ORDER, CANCEL


    // 연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    /**
     * 외부에서 값을 set 해주는 방식이 아니라 생성할 때부터 createOrder()를 호출하게 함
     * 내부에서 setter를 이용해 값을 넣어서 완결을 시킨다.
     * 주문 생성에 대한 복잡한 비즈니스 로직을 엔티티에 응집해 놓는 것
     * 이후 코드를 수정할 때, 해당 메서드만 고치면 된다.
     * 생성 메서드는 실무에서 훨씬 복잡하다.
     * 오더아이템이 넘어오는게 아니라, 파라미터나 DTO가 들어오면서 더 복잡하게 들어온다.
     */
    // 복잡한 생성은 별도의 생성 메서드가 있는게 좋다.
    // 이런 스타일로 작성하는게 좋은 점
    // OrderItem 생성자 메서드에서 재고를 감소시키고 해당 메서드 호출
    // == 생성 메서드 == //
    public static Order creatOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.getOrderItems().add(orderItem);
            orderItem.setOrder(order);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    // == 비즈니스 로직 == //
    /**
     * 주문 취소
     * 비즈니스 로직에 대한 체크 로직이 엔티티 안에 있다.
     */
    public void cancel() {
        // 이미 배송된 경우
        if (delivery.getDeliveryStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능 합니다.");
        }

        // 주문 취소 변경
        this.setOrderStatus(OrderStatus.CANCEL);

        // 루프를 돌면서 재고 상품 재고 돌려놓기
        for (OrderItem orderItem : this.orderItems) {
            // 재고수량 원복 메서드 호출
            orderItem.cancel();
        }
    }

    // ==조회 로직== //
    /**
     * 전체 주문 가격 조회
     * orderItem에 가서 개별의 주문 가격과 주문 수량의 곱해서 나온 결과가 오더 아이템의 totalPrice
     */
    // 스트림과 람다를 사용하면 코드를 깔끔하게 작성 가능
    public int getTotalPrice(){
        // Order에는 정보가 없고 OrderItem을 모두 더하면 된다.
        int totalPrice = 0;
        for (OrderItem orderItem : this.orderItems) {
            // totalPrice를 가져오는 이유는 가격과 수량이 재각각이기 때문
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
        // 스트림과 람다를 사용하면 코드를 깔끔하게 작성 가능
//        return this.orderItems.stream()
//                .mapToInt(OrderItem::getTotalPrice)
//                .sum();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", member=" + member +
                ", orderItems=" + orderItems +
                ", delivery=" + delivery +
                ", orderDate=" + orderDate +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
