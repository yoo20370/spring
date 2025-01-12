package hello.core.discount;

import hello.core.member.Member;
import hello.core.order.Order;

public interface DiscountPolicy {

    int discountPrice(Member member, int itemPrice);

    int calculationPrice(Order order);
}
