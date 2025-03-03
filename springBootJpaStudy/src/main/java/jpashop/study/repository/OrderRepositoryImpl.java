package jpashop.study.repository;

import jakarta.persistence.EntityManager;
import jpashop.study.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final EntityManager em;

    @Override
    public Long saveOrder(Order order) {
        em.persist(order);
        return order.getId();
    }

    @Override
    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    @Override
    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    // TODO: 검색 조건을 추가해서 동적 쿼리를 만들 것
}
