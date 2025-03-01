package jpabook.jpashop.entity;

import jakarta.persistence.*;
import jpabook.jpashop.entity.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 protected로 생성해줌
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; // order 필드가 연관관계 주인이 된다. 즉, 두 필드 중 어떤 필드가 FK를 관리하는지

    private int orderPrice; // 주문가격

    private int count; // 주문수량

    // 생성로직, 비즈니스로직, 조회로직을 구분해서 놓으면 보기가 편하다.
    // == 생성 메서드 == //
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        // 아이템 가격이 있으니 아이템 가격을 orderPrice로 하면 되지 않나요 ??
        // -> 쿠폰을 받거나 할인될 수 있기 때문에 따로 가져가는 것
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // 수량 만큼 아이템 재고를 감소시킨다.
        item.removeStock(count);
        return orderItem;
    }


    /**
     * 재고 수량 원복해주는 메서드
     */
    // == 비즈니스 로직 == //
    public void cancel() {
        getItem().addStock(count);
    }

    /**
     * 주문 상품 전체 가격 조회
     */
    //==조회로직==//
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
