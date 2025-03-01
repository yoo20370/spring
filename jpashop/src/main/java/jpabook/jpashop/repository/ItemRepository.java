package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.entity.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        // 아이디 값이 없으면 새로운 객체로 인식 -> 왜 ?? 영속성 컨텍스트에 없어도 DB에 존재할 수 있잖아 ??
        if (item.getId() == null) {
            // 신규 등록
            em.persist(item);
        } else {
            // 이미 DB에 등록된 것을 가져온 것 // 업데이트와 비슷한 것
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
