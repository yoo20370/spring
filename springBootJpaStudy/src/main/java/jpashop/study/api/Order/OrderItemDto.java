package jpashop.study.api.Order;

import jpashop.study.domain.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {
    private ItemDto item;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem) {
        this.item = new ItemDto(orderItem.getItem());
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}
