package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    // DIP 위반 -> MemberServiceImpl는 memberRepository 인터페이스에 의존함과 동시에 MemoryMemberRepository 구현 객체에도 의존하고 있음
    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long id) {
        return memberRepository.findById(id);
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
