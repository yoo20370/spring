package yoo.study.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yoo.study.AppConfig;
import yoo.study.member.*;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;


    @BeforeEach
    void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    @DisplayName("주문 생성하기 테스트")
    void orderCreateTest() {
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(1L, "itemA", 10000);
        System.out.println("order = " + order);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);

        Order order2 = orderService.createOrder(1L, "itemB", 20000);
        System.out.println("order = " + order2);

        Assertions.assertThat(order2.getDiscountPrice()).isEqualTo(2000);
    }
}
