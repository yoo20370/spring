package jpabook.jpashop.service;

import jpabook.jpashop.entity.item.Book;
import jpabook.jpashop.entity.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    /**
     * ItemService는 단순히 ItemRepository에 단순하게 위임만 하는 클래스
     * 위임만 하는 클래스를 만들 필요가 있는지 고민해볼 필요가 있다.
     * 직접 테스트 코드 짜볼 것
     */

    private final ItemRepository itemRepository;

    @Transactional(readOnly = false)
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, Book bookParam) {
        // 영속성 컨텍스트 혹은 DB에서 해당 객체를 가져와 영속화 시킨다.
        // 커밋 시점에 flush()를 날려서, 변경감지 수행 후 변경된 것에 대한 update 쿼리를 날림
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(bookParam.getPrice());
        findItem.setName(bookParam.getName());
        findItem.setStockQuantity(bookParam.getStockQuantity());

    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
