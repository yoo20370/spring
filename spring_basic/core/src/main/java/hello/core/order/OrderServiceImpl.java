package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        // 주문 서비스 입장에서 할인에 대한 것 모르겠다. discountPolicy 니가 알아서 해줘 - 단일 책임 원칙을 잘 지킨 것
        // 즉, 할인 변경을 수행할 때, 할인 쪽만 고치면 됨
        // member 전체를 넘겨도 되고, 등급만 넘겨도 된다. - 상황에 맞게 구현하면 됨
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
