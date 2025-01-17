package yoo.study.autowired;

import jakarta.annotation.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import yoo.study.member.Member;

import java.util.Optional;

public class AutowiredTest {



    @Test
    @DisplayName("스프링 컨테이너에서 해당 빈을 찾지 못했을 때 할 수 있는 옵션 처리")
    void AutowiredOptionTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(OptionClass.class);
    }

    static class OptionClass {

        private Member member;

        // Autowired 옵션을 준 생성자는 사용 불가, @Nullable, Optional 사용해서는 가능
        // required = false가 붙었다고 해서, 생성자 인자에 해당하는 빈이 없을 때, null을 대입하고 넘어가는 식의 로직을 지원하지 않음
//        @Autowired(required = false)
//        public OptionClass(Member member) {
//            System.out.println("member = " + member);
//        }

//        @Autowired
//        public OptionClass(@Nullable Member member) {
//            System.out.println("member = " + member);
//        }

        @Autowired
        public OptionClass(Optional<Member> member) {
            System.out.println("member = " + member);
        }
    }
}

