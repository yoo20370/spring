package hello.spring;

import hello.spring.discountpolicy.DiscountPolicy;
import hello.spring.discountpolicy.FixDiscountPolicy;
import hello.spring.discountpolicy.RateDiscountPolicy;
import hello.spring.member.MemberRepository;
import hello.spring.member.MemberService;
import hello.spring.member.MemberServiceImpl;
import hello.spring.member.MemoryMemberRepository;
import hello.spring.order.OrderService;
import hello.spring.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

    @Bean
    public DiscountPolicy discountPolicy2() {
        return new FixDiscountPolicy();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
}
