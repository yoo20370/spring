package hello.core.network;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;


class NetworkClientTest  {

    @Test
    void beanLifeCycleTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(NetworkConfig.class);


        ac.close();
    }

    static class NetworkConfig {

        @Bean
        public NetworkClient networkClient() {
            System.out.println("NetworkConfig.networkClient");
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");

            return networkClient;

        }
    }
}