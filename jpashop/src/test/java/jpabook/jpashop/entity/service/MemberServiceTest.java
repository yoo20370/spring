package jpabook.jpashop.entity.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.entity.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

// 문제점
// 실제 외부 DB 사용
// 병렬적으로 테스트를 돌리거나, DB 외부에 설치해야함
// 테스트를 완전히 격리된 환경, 자바 안에서 데이터베이스를 살짝 띄우는 방법
// 메모리 DB를 사용하여 테스트하는 것
// 스프링 부트를 사용하면 공짜로 사용 가능
/*
test 폴더 안에 resources 폴더 생성, 폴더 내부에 application.yml 복사
테스트 진행 시 테스트 폴더 안 application.yml 사용
yml 파일 내부의 url을 메모리로 변경하면 된다.
라이브러리에 h2 데이터베이스가 들어가 있음
이게 있으면 메모리 모드로 JVM 안에 띄울 수 있음
h2database.com -> cheat sheet -> In-Memory 아래 jdbc:h2:mem:test 복사
-> DB 띄우지 않고 인 메모리에 DB 띄어서 바로 테스트 가능

스프링 부트에서는 기본 설정이 없으면 메모리 모드로 돌린다.
실제 운영 환경 application.yml이랑 테스트 환경의 application.yml을 분리하는게 맞다.
내가 실제 운영 환경과 테스트 환경은 다르기 때문
내가 테스트할 때 하고 싶은 설정이랑 다름

스프링 부트는 기본적으로 creat-drop으로 기본적으로 돌아감

*/

// 아래 테스트는 순수 단위 테스트 X
@RunWith(SpringRunner.class) // Junit 실행할 때, 스프링이랑 엮어서 실행
@SpringBootTest // 스프링 부트를 띄운 상태에서 테스트
@Transactional // 같은 트랜잭션 안에서 같은 엔티티(PK가 같으면) 같은 영속성 컨텍스트에서 똑같은 애가 관리됨, 즉, 1차 캐시에서 꺼내온 것
@Rollback // 스프링에서 Transactinal은 트랜잭션 커밋을 안 하고 롤백한다. (TestCase에 있는 경우) - 테스트가 반복되야 되므로 DB에 데이터가 남으면 안 된다.
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given 주어졌을 때,
        Member member = new Member();
        member.setName("kim");

        //when 이렇게 하면
        Long savedId = memberService.join(member);
        
        //then 검증한다.
        em.flush(); // 트랜잭션 전에 호출해서 데이터베이스에 영속성 컨텍스트의 변경 내용을 반영
        Assertions.assertEquals(member, memberService.findOne(savedId));
    }
    
    @Test(expected = IllegalStateException.class) // 예외가 발생해서 함수가 예외를 던졌을 때, 설정한 예외가 맞으면 테스트 성공
    public void 중복_회원_가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");



        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member);
        memberService.join(member2);
//        Assert.fail("예외가 발생해야 한다."); // 코드가 여기까지 실행되면 테스트 실패
        //then
//        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }
}