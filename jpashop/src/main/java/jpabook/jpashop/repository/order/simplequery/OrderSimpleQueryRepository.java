package jpabook.jpashop.repository.order.simplequery;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                        "select new jpabook.jpashop.repository.OrderSimpleQueryDto(o.id, o.member.name, o.orderDate, o.orderStatus, d.address) from Order o " +
                                " join o.member m " +
                                " join o.delivery d ", OrderSimpleQueryDto.class)
                .getResultList();

    }
}
