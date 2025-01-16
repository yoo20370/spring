package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {


//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        // ApplicationContext가 스프링 컨테이너에 있는 모든 객체를 관리
        // Annotation 기반으로 설정 파일을 사용하므로 AppConfig.class의 환경 설정 정보를 가지고 스프링이 객체들을 스프링 컨테이너에 집어 넣어서 관리
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // Bean을 가져와야 함, 이 때 AppConfig에 @Bean을 사용한 메서드 이름이 컨테이너에 등록됨, 해당 메서드를 가져오도록 인자 전달, 두 번째 인자는 반환 타입
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
