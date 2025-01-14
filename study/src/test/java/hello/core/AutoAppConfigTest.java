package hello.core;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

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
    void basicScan() {
        MemberService memberService = ac.getBean("memberServiceImpl", MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }

}