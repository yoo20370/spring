package yoo.study.componentscan.Filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class MyAnnotationFilterTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(FilterConfig.class);

    @Test
    @DisplayName("내가 만든 애너테이션 필터로 빈 등록됐는지 확인하기")
    void findBeanByMyAnnotationFilter() {
        BeanA beanA = ac.getBean(BeanA.class);
        System.out.println("beanA = " + beanA);

        assertThat(beanA).isInstanceOf(BeanA.class);
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean(BeanB.class));
    }
}
