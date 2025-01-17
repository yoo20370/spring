package yoo.study.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StatefulServiceTest {

    @Test
    @DisplayName("빈이 상태를 유지하도록 설계하면 동시성 문제가 발생할 수 있다.")
    void statefulServiceTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(StatefulService.class);
        StatefulService userA = ac.getBean(StatefulService.class);
        StatefulService userB = ac.getBean(StatefulService.class);

        userA.order("userA", 10000);

        userB.order("userA", 20000);

        Assertions.assertThat(userA.getPrice()).isEqualTo(10000);

        Assertions.assertThat(userB.getPrice()).isEqualTo(20000);


    }

    static class StatefulService {

        private int price;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public void order(String userName, int price) {
            System.out.println(userName + "님이 주문한 금액은 : " + price + "원 입니다.");
            this.setPrice(price);
        }
    }
}
