package yoo.study.order;


public interface OrderService {

    Order createOrder(Long memberId, String itemName, int price);
}
