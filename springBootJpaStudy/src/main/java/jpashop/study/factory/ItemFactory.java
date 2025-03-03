package jpashop.study.factory;

import jpashop.study.domain.item.Item;
import jpashop.study.service.ItemDto;

public interface ItemFactory {
    Item creatItem(ItemDto itemDto);
    
}
