package yoo.study.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import yoo.study.AppConfig;
import yoo.study.member.MemberRepository;
import yoo.study.member.MemberService;
import yoo.study.order.OrderService;

public class SingletonBeanTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("스프링 컨테이너는 기본적으로 빈을 싱글톤 빈으로 생성한다.")
    void defaultBeanIsSingleton() {
        MemberService memberService1 = ac.getBean(MemberService.class);
        MemberService memberService2 = ac.getBean(MemberService.class);

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }

    @Test
    @DisplayName("빈, 의존관계 주입 빈도 싱글톤으로 동작한다.")
    void configurationTest() {

        MemberService memberService = ac.getBean(MemberService.class);
        OrderService orderService = ac.getBean(OrderService.class);

        MemberRepository memberRepository1 = ac.getBean(MemberRepository.class);
        MemberRepository memberRepository2 = memberService.getMemberRepository();
        MemberRepository memberRepository3 = memberService.getMemberRepository();

        System.out.println("memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository3 = " + memberRepository3);

        Assertions.assertThat(memberRepository1).isSameAs(memberRepository2);
        Assertions.assertThat(memberRepository2).isSameAs(memberRepository3);
    }
}
