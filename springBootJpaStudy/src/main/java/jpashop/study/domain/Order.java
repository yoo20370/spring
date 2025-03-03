package jpashop.study.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "Orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //==연관관계 편의 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.changeOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.changeOrder(this);
    }

    protected Order(Member member, Delivery delivery) {
        this.member = member;
        this.delivery = delivery;
        this.status = OrderStatus.ORDER;
        this.orderDate = LocalDateTime.now();
    }

    /**
     * 주문 생성 메서드(생성자 아님)
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order(member, delivery);

        order.getOrderItems().addAll(Arrays.stream(orderItems).toList());

        return order;
    }

    /**
     * 주문 취소 메서드
     */
    public void cancel() throws IllegalStateException {
        if(this.delivery.getStatus() == DeliveryStatus.COMP) throw new IllegalStateException("이미 배송이 완료된 상품입니다.");

        // 주문한 아이템 개수를 다시 증가시켜줘야 한다.
        for (OrderItem orderItem : this.orderItems) {
            orderItem.cancel();
        }

        this.status = OrderStatus.CANCEL;
    }

    /**
     * 전체 주문 금액 조회
     */
    public int getTotalPrice() {
        int orderTotalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            orderTotalPrice += orderItem.getOrderItemsToTalPrice();
        }

        return orderTotalPrice;
    }
}
