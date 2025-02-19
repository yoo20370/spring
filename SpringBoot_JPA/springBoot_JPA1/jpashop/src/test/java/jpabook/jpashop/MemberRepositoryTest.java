package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
//JUnit한테 스프링과 관련된 걸로 테스트할 것이라는 걸 알려줘야 한다.
@RunWith(SpringRunner.class)
// 스프링 부트로 테스트 할 거야.
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    // 스프링 트랜잭션 애너테이션 권장
    // 트랜잭션 애너테이션이 테스트 코드에 있으면 실행 후 바로 롤백
    @Transactional
    // @Rollback(false)를 테스트 코드에 넣으면 롤백하지 않는다.
    @Rollback(false)
    public void testMember() throws Exception{
        // EntityManager를 통한 모든 데이터 변경은 항상 트랜잭션 안에서 이루어져야 한다.

        //given
        Member member = new Member();
        member.setUserName("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
        Assertions.assertThat(findMember).isEqualTo(member);

        System.out.println("findMember = " + findMember);
        System.out.println("member = " + member);
        // findMember == member가 true인 이유
        // 같은 트랜잭션 안에서 영속성 컨텍스트가 동일하다.
        // 같은 영속성 컨텍스트 안에서는 식별자가 같으면 같은 엔티티로 인식
    }
}