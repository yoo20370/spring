package yoo.study.order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yoo.study.discount.DiscountPolicy;
import yoo.study.member.Member;
import yoo.study.member.MemberRepository;


@Component
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int price) {
        // 멤버 서비스의 조회 시스템을 활용회 회원 조회
        Member member = memberRepository.findById(memberId);

        // 할인된 금액 조회
        int discountPrice = discountPolicy.calculationDiscountPrice(member, price);

        // Order 객체 만들어서 반환
        return new Order(memberId, itemName, price, discountPrice);
    }

    @Override
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }
}
