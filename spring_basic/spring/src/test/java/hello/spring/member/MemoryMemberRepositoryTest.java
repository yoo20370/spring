package hello.spring.member;

import hello.spring.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class MemoryMemberRepositoryTest {

    MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        memberRepository = ac.getBean(MemberRepository.class);
    }

    @Test
    @DisplayName("회원 등록 및 조회 테스트")
    void memberJoinAndFindTest() {
        Member member = new Member(1L, "MemberA", Grade.VIP);
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId());

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    }
}