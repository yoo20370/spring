package yoo.study;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yoo.study.discount.DiscountPolicy;
import yoo.study.discount.FixDiscountPolicy;
import yoo.study.discount.RateDiscountPolicy;
import yoo.study.member.MemberRepository;
import yoo.study.member.MemberService;
import yoo.study.member.MemberServiceImpl;
import yoo.study.member.MemoryMemberRepository;
import yoo.study.order.OrderService;
import yoo.study.order.OrderServiceImpl;


@Configuration
public class AppConfig {
    // 객체를 생성하고 연결해주는 역할을 수행하는 설정 정보

    // 스프링 컨테이너에 빈을 등록할 때 , @Bean이 붙은 메서드를 호출하고, 빈 저장소에 메서드 이름을 빈 이름으로하고 반환되는 객체를 빈으로 저장한다.
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
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
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
}
