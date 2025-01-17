package yoo.study.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import yoo.study.member.Grade;
import yoo.study.member.Member;

@Component
@Qualifier("fixDiscountPolicy")
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
