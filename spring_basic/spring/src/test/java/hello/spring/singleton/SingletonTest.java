package hello.spring.singleton;

import hello.spring.AppConfig;
import hello.spring.member.MemberRepository;
import hello.spring.order.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingletonTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("동일한 타입을 조회했을 때, 동일한 객체가 반환된다.")
    void singleton() {

        OrderService orderService = ac.getBean(OrderService.class);

        MemberRepository memberRepository1 = ac.getBean(MemberRepository.class);
        MemberRepository memberRepository2 = ac.getBean(MemberRepository.class);
        MemberRepository memberRepository3 = orderService.getMemberRepository();

        System.out.println("memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository3 = " + memberRepository3);

        Assertions.assertThat(memberRepository1).isSameAs(memberRepository2);
        Assertions.assertThat(memberRepository1).isSameAs(memberRepository3);
    }
}
