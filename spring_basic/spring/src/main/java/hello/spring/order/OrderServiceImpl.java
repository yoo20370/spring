package hello.spring.order;

import hello.spring.discountpolicy.DiscountPolicy;
import hello.spring.discountpolicy.FixDiscountPolicy;
import hello.spring.discountpolicy.MainDiscountPolicy;
import hello.spring.member.Member;
import hello.spring.member.MemberRepository;
import hello.spring.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository,@MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, Integer price) {

        Member member = memberRepository.findById(memberId);
        Integer discountPrice = discountPolicy.getDiscountPrice(member, price);

        return new Order(itemName, memberId, price, discountPrice);
    }

    @Override
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
