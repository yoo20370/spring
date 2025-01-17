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

        // 객체 생성은 객체를 생성해서 필수값들을 설정하는 것까지 구현하는 것이 좋다.
        // DB를 연결하거나 하는 작업은 무거운 작업이므로 객체 생성에 넣지 말고 초기화 작업에서 수행하는 것이 좋다.
        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://yoo-stude.dev");
            return networkClient;
        }
    }
}
