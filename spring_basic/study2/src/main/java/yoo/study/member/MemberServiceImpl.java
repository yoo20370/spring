package yoo.study.member;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {


    private MemberRepository memberRepository;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        System.out.println("Auto Setter DI");
        this.memberRepository = memberRepository;
    }

//    @Autowired
//    public MemberServiceImpl(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }

    public void init() {
        System.out.println("MemberServiceImpl.init");
    }

    public void destroy(){

    }
}
