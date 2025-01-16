package yoo.study.order;


import yoo.study.discount.DiscountPolicy;
import yoo.study.discount.FixDiscountPolicy;
import yoo.study.member.Member;
import yoo.study.member.MemberService;
import yoo.study.member.MemberServiceImpl;

public class OrderServiceImpl implements OrderService {

    // DIP 위반 - 구현 객체에 의존하고 있음
    private final MemberService memberService = new MemberServiceImpl();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int price) {
        // 멤버 서비스의 조회 시스템을 활용회 회원 조회
        Member member = memberService.findMember(memberId);

        // 할인된 금액 조회
        int discountPrice = discountPolicy.calculationDiscountPrice(member, price);

        // Order 객체 만들어서 반환
        return new Order(memberId, itemName, price, discountPrice);
    }
}
