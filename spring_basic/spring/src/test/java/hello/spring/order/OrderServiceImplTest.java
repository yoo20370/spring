package hello.spring.order;

import hello.spring.AppConfig;
import hello.spring.member.Grade;
import hello.spring.member.Member;
import hello.spring.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class OrderServiceImplTest {

    MemberRepository memberRepository;
    OrderService orderService;

    @BeforeEach
    void beforeEach() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        memberRepository = ac.getBean(MemberRepository.class);
        orderService = ac.getBean(OrderService.class);
    }

    @Test
    @DisplayName("주문이 제대로 만들어지는지 테스트")
    void checkCreateOrder() {
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberRepository.save(member);

        Order order = orderService.createOrder(member.getId(), "itemA", 10000);
        Order order2 = orderService.createOrder(member.getId(), "itemA", 20000);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
        Assertions.assertThat(order2.getDiscountPrice()).isEqualTo(2000);
    }

}