package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscontPolicy;
import hello.core.member.*;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;


class AutoAppConfigTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionNams : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionNams);
            System.out.println("beanName = " + beanDefinitionNams + " bean = " + bean);
        }
    }

    @Test
    @DisplayName("내가 직접 등록한 빈만 출력하기")
    void findApplicationBean() {
        String[] beansDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beansDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("beanName = " + beanDefinitionName + " bean = " + bean);
            }
        }
    }

    @Test
    @DisplayName("컴포넌트 스캔")
    void clientBean() {
        MemberService memberService = ac.getBean("memberServiceImpl", MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }

    @Test
    void fieldDependencyInjectTest() {
        // DI 컨테이너의 도움 없이 코드를 작성하려고 할 떄, 의존성 주입을 할 수 없으므로 테스트하기 어려움
        // 아래 코드는 생성자를 이용했기 떄문에 의존성 주입이 가능
        MemberService memberService = new MemberServiceImpl(new MemoryMemberRepository());
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        // OrderServiceImpl에 MemberRepository 의존성을 주입해주기 위해 가져온 것
        MemberRepository memberRepository = memberService.getMemberRepository();

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new RateDiscontPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

    @Test
    void findAllBeanByType() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DiscountService.class, AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        DiscountService discountService = ac.getBean(DiscountService.class);

        int result1 = discountService.discount(member, 10000, "fixDiscountPolicy");
        Assertions.assertThat(result1).isEqualTo(1000);

        int result2 = discountService.discount(member, 20000, "rateDiscontPolicy");
        Assertions.assertThat(result2).isEqualTo(2000);
    }

    static class DiscountService {

        private final Map<String, DiscountPolicy> policyMap;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap) {
            System.out.println("policyMap = " + policyMap);
            this.policyMap = policyMap;
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discountPrice(member, price);
        }
    }

}