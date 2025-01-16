package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA: A 사용자 10000원 주문
        int userAPrice = statefulService1.order("userA", 10000);

        // ThreadBL B 사용자 20000원 주문
        int userBPrice = statefulService1.order("userB", 20000);

        // ThreadA : 사용자A가 주문 금액을 조회
//        System.out.println("statefulService1.getPrice() = " + statefulService1.getPrice());
        System.out.println("statefulService1.getPrice() = " + userAPrice);
        // 싱글톤 패턴을 사용해 동일한 객체의 price 변수에 값을 저장하므로 마지막으로 주문한 사용자의 값이 저장됨
        Assertions.assertThat(userAPrice).isEqualTo(10000);

        // ThreadB : 사용자B가 주문 금액을 조회
//        System.out.println("statefulService2.getPrice() = " + statefulService2.getPrice());
        System.out.println("statefulService2.getPrice() = " + userBPrice);
        Assertions.assertThat(userBPrice).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }

    }
}