package yoo.study.member;

import jakarta.annotation.PostConstruct;

public class MemberServiceImpl implements MemberService {

    // DIP 위반 - MemberRepository에만 의존하지 않고 구현체인 MemoryMemberRepository에도 의존하고 있음
    private final MemberRepository memberRepository;

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


    public void init() {
        System.out.println("MemberServiceImpl.init");
    }
}
