package hello.spring.order;

import hello.spring.member.MemberRepository;

public interface OrderService {

    Order createOrder(Long memberId, String itemName, Integer price);

    MemberRepository getMemberRepository();
}
