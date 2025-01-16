package yoo.study.beanfind;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import yoo.study.AppConfig;
import yoo.study.discount.DiscountPolicy;
import yoo.study.discount.FixDiscountPolicy;
import yoo.study.discount.RateDiscountPolicy;
import yoo.study.member.MemberRepository;
import yoo.study.member.MemberService;
import yoo.study.order.OrderService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("bean = " + bean + "beanDefinitionName = " + beanDefinitionName);
        }
    }

    @Test
    @DisplayName("내가 등록한 빈만 출력하기")
    void findMyRegiBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("bean = " + bean + "beanDefinitionName = " + beanDefinitionName);
            }
        }
    }

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanName1() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }

    @Test
    @DisplayName("빈 타입으로 조회")
    void findBeanType() {
        MemberRepository memberRepository = ac.getBean(MemberRepository.class);
        Assertions.assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByType2() {
        DiscountPolicy discountPolicy = ac.getBean("discountPolicy", RateDiscountPolicy.class);
        Assertions.assertThat(discountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("해당하는 빈 이름 없는 경우")
    void findBeanName2() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("xxxx", MemberService.class));
    }

    @Test
    @DisplayName("동일한 타입이 둘 이상인 경우 오류 발생")
    void findBeanByTypeDuplicate() {
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("동일한 타입이 둘 이상이 경우 빈이름을 지정하면 된다.")
    void findBeanByName() {
        DiscountPolicy discountPolicy = ac.getBean("discountPolicy2", DiscountPolicy.class);
        Assertions.assertThat(discountPolicy).isInstanceOf(FixDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findBeansOfType() {
        Map<String, DiscountPolicy> policyMap =  ac.getBeansOfType(DiscountPolicy.class);
        Assertions.assertThat(policyMap.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("모든 빈 조회화기 ")
    void findBeansOfObject() {
        Map<String, Object> objectMap = ac.getBeansOfType(Object.class);
        for (String key : objectMap.keySet()) {
            System.out.println("beanName = " + key + " bean = " + objectMap.get(key));
        }
    }

    @Test
    @DisplayName("xml 조회해보기")
    void xmlAppContext() {
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext("AppConfig.xml");

        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);

        OrderService orderService = ac.getBean(OrderService.class);
        Assertions.assertThat(orderService).isInstanceOf(OrderService.class);
    }
}
