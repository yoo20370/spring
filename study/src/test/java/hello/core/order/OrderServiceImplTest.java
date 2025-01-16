package hello.core.order;


import hello.core.AppConfig;
import hello.core.AutoAppConfig;
import hello.core.member.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class OrderServiceImplTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    void beforeEach() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        memberService = ac.getBean("memberServiceImpl", MemberService.class);
        orderService = ac.getBean("orderServiceImpl", OrderService.class);
    }

    @Test
    void createOrderTest() {
        long memberId = 1L;

        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }


}