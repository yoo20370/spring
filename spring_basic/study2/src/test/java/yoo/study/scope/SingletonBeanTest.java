package yoo.study.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonBeanTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class);

    @Test
    @DisplayName("싱글톤 스코프 빈의 경우 요청할 때마다 동일한 빈을 반환해야한다.")
    void singletonTest() {
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        ClientBean clientBean3 = ac.getBean(ClientBean.class);

        System.out.println("clientBean1 = " + clientBean1);
        System.out.println("clientBean2 = " + clientBean2);
        System.out.println("clientBean3 = " + clientBean3);

        assertThat(clientBean1).isSameAs(clientBean2);
        assertThat(clientBean1).isSameAs(clientBean3);
    }

    @Scope("singleton")
    static class ClientBean {

        @PostConstruct
        public void init() {
            System.out.println("ClientBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("ClientBean.destroy");
        }

    }
}
