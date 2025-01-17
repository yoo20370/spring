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

        int price1 = userA.order("userA", 10000);
        int price2 = userB.order("userB", 20000);

        Assertions.assertThat(price1).isEqualTo(10000);
        Assertions.assertThat(price2).isEqualTo(20000);
    }

    static class StatefulService {

        public int order(String userName, int price) {
            System.out.println(userName + "님이 주문한 금액은 : " + price + "원 입니다.");
            return price;
        }
    }
}
