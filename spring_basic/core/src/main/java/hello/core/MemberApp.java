package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp {

    // 순수 자바 코드, 자바 메서드 실행, 스프링 메서드 없음
    public static void main(String[] args) {

        // 현재는 AppConfig를 통해서
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();

        // 기존에는 main 메서드에서 직접 생성해서 사용
//        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
