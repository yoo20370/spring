package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.order.Order;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FixDiscountPolicy implements DiscountPolicy{

    @Override
    public int calculationPrice(Order order) {
        return order.getPrice() - order.getDiscountPrice();
    }

    @Override
    public int discountPrice(Member member, int itemPrice) {
        if(member.getGrade() == Grade.VIP) {
            return 1000;
        } else {
            return 0;
        }
    }
}
