package yoo.study.discount;

import yoo.study.member.Grade;
import yoo.study.member.Member;

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
