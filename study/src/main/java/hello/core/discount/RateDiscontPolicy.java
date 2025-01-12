package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.order.Order;

public class RateDiscontPolicy implements DiscountPolicy{

    int discountPercent = 10;
    @Override
    public int discountPrice(Member member, int itemPrice) {

        if(member.getGrade() == Grade.VIP) {
            return itemPrice * discountPercent / 100;
        } else {
            return 0;
        }


    }

    @Override
    public int calculationPrice(Order order) {
        return order.getPrice() - order.getDiscountPrice();
    }
}