package yoo.study.componentscan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import yoo.study.AutoAppConfig;
import yoo.study.member.MemberService;

public class ComponentScanTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

    @Test
    @DisplayName("컴포넌트 스캔을 통한 자동 주입 테스트")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("bean = " + bean + "beanDefinitionName = " + beanDefinitionName);
        }
    }

    @Test
    @DisplayName("컴포넌트 스캔을 통한 빈 등록을 증명하기 위해 특정 타입으로 검색")
    void findBeanByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
