package yoo.study.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yoo.study.member.Grade;
import yoo.study.member.Member;
import yoo.study.member.MemberRepository;
import yoo.study.member.MemoryMemberRepository;

public class OrderServiceTest {

    OrderService orderService = new OrderServiceImpl();
    MemberRepository memberRepository = new MemoryMemberRepository();

    @Test
    @DisplayName("주문 생성하기 테스트")
    void orderCreateTest() {
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberRepository.save(member);

        Order order = orderService.createOrder(1L, "itemName", 10000);
        System.out.println("order = " + order);

        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
