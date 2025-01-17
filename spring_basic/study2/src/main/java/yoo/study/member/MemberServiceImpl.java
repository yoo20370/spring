package yoo.study.member;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    // 필드 주입의 경우 스프링 컨테이너에 의존적
    // 즉, 자바 순수 코드로 테스트 코드 작성 시, 외부에서 변경이 불가능하므로 테스트 코드 작성이 어렵다.
    @Autowired
    private MemberRepository memberRepository;

//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("Auto Setter DI");
//        this.memberRepository = memberRepository;
//    }

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
