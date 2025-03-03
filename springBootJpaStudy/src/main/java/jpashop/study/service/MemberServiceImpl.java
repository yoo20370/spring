package jpashop.study.service;

import jakarta.transaction.Transactional;
import jpashop.study.domain.Member;
import jpashop.study.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Long join(Member member) throws IllegalStateException {
        // 멀티 쓰레드 환경에서 동시성 문제가 발생할 수 있다.
        validationDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validationDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Override
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
