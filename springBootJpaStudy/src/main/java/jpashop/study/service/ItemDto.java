package jpashop.study.service;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDto {

    private Long itemId;
    private String itemName;
    private int itemPrice;
    private int itemStockQuantity;
    private String dtype;
}
