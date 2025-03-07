package jpashop.study.repository.query;

import jakarta.persistence.EntityManager;
import jpashop.study.api.Order.simpleorder.OrderDto;
import jpashop.study.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleRepository {

    private final EntityManager em;

    public List<Order> findAll() {

        // 페이징 불가
        return em.createQuery("select distinct o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d " +
                        " join fetch o.orderItems oi" +
                        " join fetch oi.item ", Order.class)
                .getResultList();
    }

    public List<OrderDto> findOrderDtos() {
        return em.createQuery("select new jpashop.study.api.Order.simpleorder.OrderDto(o.id, m.name , o.orderDate, o.status, d.address) from Order o " +
                        " join o.member m" +
                        " join o.delivery d", OrderDto.class)
                .getResultList();
    }
}
