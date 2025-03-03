package jpashop.study.service;

import jpashop.study.domain.Order;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface OrderService {

    Long order(Long memberId, Long itemId, int count);

    void cancelOrder(Long orderId);

    Order findOne(Long orderId);

    List<Order> findAll();
}
