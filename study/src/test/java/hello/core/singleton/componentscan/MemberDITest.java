package hello.core.singleton.componentscan;

import hello.core.AutoAppConfig;
import hello.core.member.Member;
import hello.core.member.MemberService;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class MemberDITest {

    @Test
    void dependencyOptionTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(OptionBean.class, AutoAppConfig.class);
    }


    static class OptionBean {

        @Autowired
        public void setOptionBean(@Autowired(required = false) Member member, MemberService memberService) {
            System.out.println("member = " + member);
            System.out.println("memberService = " + memberService);
        }

        @Autowired
        public void setOptionBean2(@Nullable Member member, MemberService memberService) {
            System.out.println("member = " + member);
            System.out.println("memberService = " + memberService);
        }

        @Autowired
        public void setOptionBean3(Optional<Member> member, Optional<MemberService> memberService) {
            if (!member.isEmpty()) {
                System.out.println("member = " + member.get());
            } else {
                System.out.println("member = " + member);
            }

            System.out.println("memberService = " + memberService);
        }
    }
}
