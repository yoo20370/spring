package yoo.study.order;


import yoo.study.member.MemberRepository;

public interface OrderService {

    Order createOrder(Long memberId, String itemName, int price);

    MemberRepository getMemberRepository();

    void init();

    void destroy();
}
