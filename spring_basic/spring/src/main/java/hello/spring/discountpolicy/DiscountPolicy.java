package hello.spring.discountpolicy;

import hello.spring.member.Member;

public interface DiscountPolicy {

    int getDiscountPrice(Member member, Integer price);
}
