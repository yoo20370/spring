package hello.spring.discountpolicy;

import hello.spring.member.Grade;
import hello.spring.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    @Override
    public int getDiscountPrice(Member member, Integer price) {
        if (member.getGrade() == Grade.VIP) {
            return 1000;
        } else {
            return 0;
        }
    }
}
