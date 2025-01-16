package yoo.study.discount;

import yoo.study.member.Grade;
import yoo.study.member.Member;

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
