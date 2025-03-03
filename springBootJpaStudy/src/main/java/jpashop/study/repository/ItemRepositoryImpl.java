package jpashop.study.repository;

import jakarta.persistence.EntityManager;
import jpashop.study.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final EntityManager em;

    // 상품 등록
    @Override
    public Long save(Item item) {
        em.persist(item);
        return item.getId();
    }

    // 상품 찾기
    @Override
    public Item findOne(Long itemId) {
        return em.find(Item.class, itemId);
    }

    // 모든 상품 찾기
    @Override
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    @Override
    public void updateItemInfo(Long itemId, String name, int price, int stockQuantity) {
        // 영속화
        Item findItem = em.find(Item.class, itemId);

        // 변경 감지 이용
        findItem.changeName(name);
        findItem.changePrice(price);
        findItem.changeStockQuantity(stockQuantity);
    }

    @Override
    public List<Item> findByName(String name) {
        return List.of();
    }
}
