package yoo.study.discount;

import org.springframework.stereotype.Component;
import yoo.study.member.Grade;
import yoo.study.member.Member;

@Component
public class FixDiscountPolicy implements DiscountPolicy{

    int fixDiscountPrice = 1000;
    @Override
    public int calculationDiscountPrice(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return fixDiscountPrice;
        } else {
            return 0;
        }
    }
}
