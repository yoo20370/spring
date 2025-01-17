package yoo.study.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import yoo.study.annotation.MainDiscountPolicy;
import yoo.study.member.Grade;
import yoo.study.member.Member;

@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{

    int discountPercent = 10;
    @Override
    public int calculationDiscountPrice(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
