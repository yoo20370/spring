package jpashop.study.repository;

import jpashop.study.domain.Order;
import jpashop.study.domain.OrderSearch;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository {

    Long saveOrder(Order order);

    Order findOne(Long orderId);

    List<Order> findAll();

//    List<Order> findByString(OrderSearch orderSearch)

    List<Order> findAllByString(OrderSearch orderSearch);
}
