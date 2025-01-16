package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscontPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{


    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired(required = false)
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
        System.out.println("discountPolicy = " + discountPolicy);
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int price) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discountPrice(member, price);

        return new Order(memberId, itemName, price, discountPrice);
    }

    public MemberRepository getMemberRepository(){
        return memberRepository;
    }

}
