package yoo.study.discount;

import yoo.study.member.Member;

public interface DiscountPolicy {

    int calculationDiscountPrice(Member member, int price);
}
