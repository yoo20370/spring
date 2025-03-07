package jpashop.study.api.Order;

import jpashop.study.domain.item.Item;
import lombok.Data;

@Data
public class ItemDto {

    private String itemName;
    private int itemPrice;
    private int itemStockQuantity;

    public ItemDto(Item item) {
        this.itemName = item.getName();
        this.itemPrice = item.getPrice();
        this.itemStockQuantity = item.getStockQuantity();
    }
}
