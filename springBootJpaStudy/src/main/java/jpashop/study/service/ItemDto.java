package jpashop.study.service;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemDto {

    private Long itemId;
    private String name;
    private int price;
    private int stockQuantity;
    private String dtype;
}
