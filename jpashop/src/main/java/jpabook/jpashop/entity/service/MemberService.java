package jpabook.jpashop.entity.service;

import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 컴포넌트 스캔 대상, 추가 기능 없음,
// 단순히 개발자들이 해당 애너테이션을 보고 비즈니스 로직임을 알아차릴 수 있음
@Service
// 클래스 레벨에 사용하면, public 메서드들에 기본적으로 걸려들어감 - 스프링 어노테이션 사용 추천
// 쓰기 작업이 많으므로 클래스 레벨에 정의
@Transactional(readOnly = true)
//@AllArgsConstructor - 모든 필드를 갖는 생성자 생성
@RequiredArgsConstructor // final 있는 필드를 가지고 생성자 생성
public class MemberService {

    // 불변, 필수 설정 - 값이 들어오지 않으면 컴파일 시점에서 확인해줌
    private final MemberRepository memberRepository;

    // 생성자가 하나일 때는 @Autowired 생략 가능
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional // 기본이 readOnly = false
    public Long join(Member member){
        // WAS에 사용자가 동시에 동일한 이름으로 insert 하게 되면, 문제가 발생할 수 있다.
        // (동시에 가입 한 경우, 멀티 쓰레드 환경에서 해당 함수가 원자적으로 실행되지 않으므로(쓰레드 문맥교환 발생) 경쟁상태로 둘 다 저장될 수 있음 )
        // 실무에서는 데이터베이스에 이름을 유니크 제약 조건으로 걸어줘야 한다. - 이는 동시성 문제를 직접적으로 해결하는 방법은 아니다.
        // 단, 동시 요청이 많아도 중복 방지 가능 -> 내부 충돌 발생시 DuplicateKeyException

        validateDuplicateMember(member);
        // 첫 번째 쓰레드가 조건을 모두 통과하고 해당 라인에서 저장하지 못한체 쓰레드 문맥교환 시,
        // 다음 쓰레드가 조건을 모두 통과하므로 회원가입 가능,
        // 이후 첫 번째 쓰레드가 회원 가입을 수행하면 동일한 이름으로 가입
        return memberRepository.save(member);

    }

    // 중복회원이면 예외를 터뜨릴 것이다.
    public void validateDuplicateMember(Member member) {

        List<Member> findMembers = memberRepository.findByName(member.getName());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
//    @Transactional(readOnly = true) // readOnly = true를 설정하면 JPA가 조회하는 곳에서 성능을 최적화
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 한 회원만 조회
//    @Transactional(readOnly = true)
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

}
