package yoo.study.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yoo.study.AppConfig;

public class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    @DisplayName("회원가입 및 조회 잘 되는지 테스트")
    void joinAndFindTest() {
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(member.getId());
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
