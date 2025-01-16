package yoo.study.order;


import yoo.study.discount.DiscountPolicy;
import yoo.study.discount.FixDiscountPolicy;
import yoo.study.discount.RateDiscountPolicy;
import yoo.study.member.Member;
import yoo.study.member.MemberRepository;
import yoo.study.member.MemberService;
import yoo.study.member.MemberServiceImpl;

public class OrderServiceImpl implements OrderService {

    // 직접 참조를 제거하여 DIP를 지킴
    // 외부에서 AppConfig를 통해서 의존관계에 있는 객체를 주입해줌으로써 OCP를 지킴
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    // OCP 위반 - 할인 정책만 교체했을 뿐인데, 클라이언트인 OrderServiceImpl의 코드가 변경됨 -> 관심사 분리 필요
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();


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
}
