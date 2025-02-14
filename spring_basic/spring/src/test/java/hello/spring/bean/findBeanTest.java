package hello.spring.bean;

import hello.spring.AppConfig;
import hello.spring.discountpolicy.DiscountPolicy;
import hello.spring.member.MemberRepository;
import hello.spring.member.MemberService;
import hello.spring.member.MemberServiceImpl;
import hello.spring.order.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class findBeanTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("등록한 모든 빈 조회하기")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println(bean);
        }
    }

    @Test
    @DisplayName("등록한 모든 빈 조회하기")
    void findMyBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println(bean);
            }
        }
    }

    @Test
    @DisplayName("빈 이름으로 조회하기")
    void findBeanByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        DiscountPolicy discountPolicy = ac.getBean("discountPolicy", DiscountPolicy.class);

        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
        Assertions.assertThat(discountPolicy).isInstanceOf(DiscountPolicy.class);
    }

    @Test
    @DisplayName("빈 타입으로 조회하기")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        OrderService orderService = ac.getBean(OrderService.class);

        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
        Assertions.assertThat(orderService).isInstanceOf(OrderService.class);
    }

    @Test
    @DisplayName("타입으로 조회했을 때, 동일한 타입이 두 개 이상인 경우 빈 이름을 지정해주면된다.(구체타입)")
    void findBeanByType2() {
        org.junit.jupiter.api.Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("특정 타입 모두 조회하기")
    void findBeansOfType() {
        Map<String, DiscountPolicy> beans = ac.getBeansOfType(DiscountPolicy.class);
        for (String key : beans.keySet()) {
            System.out.println(key +" = "+ beans.get(key));

        }
        Assertions.assertThat(beans.size()).isEqualTo(2);
    }

}
