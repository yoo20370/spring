package yoo.study;

import yoo.study.discount.DiscountPolicy;
import yoo.study.discount.RateDiscountPolicy;
import yoo.study.member.MemberRepository;
import yoo.study.member.MemberService;
import yoo.study.member.MemberServiceImpl;
import yoo.study.member.MemoryMemberRepository;
import yoo.study.order.OrderService;
import yoo.study.order.OrderServiceImpl;

public class AppConfig {

    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
}
