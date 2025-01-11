package hello.core.member;

// 구현체가 하나일 때는 관례상 Impl이라고 많이 사용
public class MemberServiceImpl implements MemberService {

    // MemoryMemberRepository 구현 객체 선택
    // 할당하는 부분이 추상화(인터페이스)에 의존하면서 구현체에도 의존하고 있음 DIP 위반
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
