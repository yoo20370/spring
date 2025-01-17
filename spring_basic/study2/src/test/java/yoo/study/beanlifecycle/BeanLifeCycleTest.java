package yoo.study.beanlifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    void lifeCycleTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycle.class);
        NetworkClient networkClient = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycle {

        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://yoo-stude.dev");
            return networkClient;
        }
    }
}
