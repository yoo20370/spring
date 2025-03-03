package jpashop.study.service;

import jpashop.study.domain.item.Album;
import jpashop.study.domain.item.Item;
import jpashop.study.factory.ItemFactoryImpl;
import jpashop.study.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService{

    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public void saveItem(ItemDto itemDto) {
        Item newItem = new ItemFactoryImpl().creatItem(itemDto);
        if(newItem != null) itemRepository.save(newItem);
    }

    @Override
    @Transactional
    public void updateItemInfo(ItemDto itemDto) {
        // 영속화 시킴
        Item findItem = itemRepository.findOne(itemDto.getItemId());

        // 엔티티에 작성된 비즈니스 로직을 호출하여 특정 데이터 변경
        // 변경된 데이터에 대하여 변경감지를 통해 수정되도록 함
        findItem.updateItem(itemDto);
    }

    // 단순 위임
    @Override
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // 단순 위임
    @Override
    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
