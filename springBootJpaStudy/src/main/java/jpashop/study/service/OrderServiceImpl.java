package jpashop.study.service;

import jpashop.study.domain.*;
import jpashop.study.domain.item.Item;
import jpashop.study.exception.NotEnoughStockException;
import jpashop.study.repository.ItemRepository;
import jpashop.study.repository.MemberRepository;
import jpashop.study.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    /**
     * 하나의 아이템을 주문하는 것 - 예제를 간단하게 하기 위함
     */
    @Override
    public Long order(Long memberId, Long itemId, int count) throws NotEnoughStockException {
        Member findMember = memberRepository.findOne(memberId);
        Item findItem = itemRepository.findOne(itemId);

        OrderItem newOrderItem = OrderItem.createOrderItem(findItem, findItem.getPrice(), count);

        Delivery newDelivery = Delivery.createDelivery(findMember.getAddress());

        Order newOrder = Order.createOrder(findMember, newDelivery, newOrderItem);

        orderRepository.saveOrder(newOrder);
        return newOrder.getId();
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order findOrder = orderRepository.findOne(orderId);
        // 주문 취소
        findOrder.cancel();
    }

    @Override
    public Order findOne(Long orderId) {
        return orderRepository.findOne(orderId);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
