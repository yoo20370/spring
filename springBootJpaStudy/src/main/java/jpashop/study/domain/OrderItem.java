package jpashop.study.domain;

import jakarta.persistence.*;
import jpashop.study.domain.item.Item;
import jpashop.study.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    //주문가격(할인 포함)
    private int orderPrice;

    //주문수량
    private int count;

    protected OrderItem(Item item, int orderPrice, int count) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;

    }

    // 생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) throws NotEnoughStockException {
        item.removeStock(count);
        return new OrderItem(item, orderPrice, count);
    }

    // 비즈니스 로직
    /**
     * 주문 취소시, 재고물량을 다시 올려줘야 함
     */
    public void cancel() {
        this.item.addStock(count);
    }

    /**
     * 아이템 총 주문 금액
     */
    public int getOrderItemsToTalPrice() {
        return count * item.getPrice();
    }

    public void changeOrder(Order order) {
        this.order = order;
    }
}
