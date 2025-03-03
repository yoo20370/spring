package jpashop.study.repository;

import jpashop.study.domain.item.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository {

    Long save(Item item);

    Item findOne(Long itemId);

    List<Item> findAll();

    void updateItemInfo(Long itemId, String name, int price, int stockQuantity);

    List<Item> findByName(String name);
}
