package hello.spring.order;

import hello.spring.discountpolicy.DiscountPolicy;
import hello.spring.discountpolicy.FixDiscountPolicy;
import hello.spring.member.Member;
import hello.spring.member.MemberRepository;
import hello.spring.member.MemoryMemberRepository;


// 전력 디자인 패턴
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, Integer price) {

        Member member = memberRepository.findById(memberId);
        Integer discountPrice = discountPolicy.getDiscountPrice(member, price);

        return new Order(itemName, memberId, price, discountPrice);
    }
}
