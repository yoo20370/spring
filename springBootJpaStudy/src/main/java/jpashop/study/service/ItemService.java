package jpashop.study.service;

import jpashop.study.domain.item.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {

    void saveItem(ItemDto itemDto);

    void updateItemInfo(ItemDto itemDto);

    Item findOne(Long itemId);

    List<Item> findItems();
}
