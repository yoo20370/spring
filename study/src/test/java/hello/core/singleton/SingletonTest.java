package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;

public class SingletonTest {

    @Test
    @DisplayName("스프링에 의해 관리되는 객체는 싱긅톤이다.")
    void getSpringBean() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        Assertions.assertThat(memberService1).isSameAs(memberService2);
    }

    @Test
    @DisplayName("무상태로 만들지 않을 경우 발생하는 문제")
    void statefulServiceTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestC.class);

        TestService userA = ac.getBean("testService", TestService.class);
        TestService userB = ac.getBean("testService", TestService.class);

        userA.order("userA", 10000);

        userB.order("userB", 20000);

        // 동일한 객체의 공유 자원인 price에 스레드가 번갈아 점유되며 접근하게 되어 경쟁상태가 발생한 것
        Assertions.assertThat(userA.getPrice()).isEqualTo(userB.getPrice());
    }

    @Test
    @DisplayName("무상태로 만들어 동시성 문제 해결 ")
    void statefulLessServiceTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestC.class);

        TestService2 userA = ac.getBean("testService2", TestService2.class);
        TestService2 userB = ac.getBean("testService2", TestService2.class);

        HashMap<String, Object> userAData = userA.order("userA", 10000);
        HashMap<String, Object> userBData = userA.order("userB", 20000);

        String userAName = userAData.get("name").toString();
        int userAPrice = (int)userAData.get("price");
        System.out.println("userAName = " + userAName);

        String userBName = userBData.get("name").toString();
        int userBPrice = (int)userBData.get("price");
        System.out.println("userBName = " + userBName);

        // 동일한 객체의 공유 자원인 price에 스레드가 번갈아 점유되며 접근하게 되어 경쟁상태가 발생한 것
        Assertions.assertThat(userAPrice).isNotEqualTo(userBPrice);
    }

    @Test
    @DisplayName("서로 다른 객체 속에 사용되는 같은 타입의 객체는 동일한 객체이다.")
    void singletonTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository memberRepository1 = ac.getBean("memberRepository", MemberRepository.class);
        MemberRepository memberRepository2 = memberService.getMemberRepository();
        MemberRepository memberRepository3 = orderService.getMemberRepository();

        Assertions.assertThat(memberRepository1).isSameAs(memberRepository2);
        Assertions.assertThat(memberRepository2).isSameAs(memberRepository3);
    }

    @Test
    @DisplayName("@Configuration 애너테이션을 붙이지 않는 경우 - 스프링 컨테이너에 @Bean이 붙은 메서드는 실행되어 빈이 등록되지만 싱글톤 패턴을 보장하지 않음")
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository memberRepository1 = ac.getBean("memberRepository", MemberRepository.class);
        MemberRepository memberRepository2 = memberService.getMemberRepository();
        MemberRepository memberRepository3 = orderService.getMemberRepository();

        Assertions.assertThat(memberRepository1).isSameAs(memberRepository2);
        Assertions.assertThat(memberRepository2).isSameAs(memberRepository3);
    }

    @Configuration
    static class TestC {

        @Bean
        public TestService testService() {
            return new TestService();
        }

        @Bean
        public TestService2 testService2() {
            return new TestService2();
        }
    }


}
