package hello.core.singleton.componentscan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class HelloConfigTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(HelloConfig.class);

    @Test
    @DisplayName("사용자 정의 애너테이션을 갖고 있는 빈이 등록됐는지 체크")
    void filterTest() {
        BeanA A = ac.getBean("beanA", BeanA.class);

        assertThat(A).isNotNull();
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("beanB", BeanB.class));
    }

}