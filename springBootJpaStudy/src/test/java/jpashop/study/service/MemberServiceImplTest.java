package jpashop.study.service;

import jpashop.study.domain.Address;
import jpashop.study.domain.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceImplTest {

    @Autowired
    MemberServiceImpl memberService;



    @Test
    @Transactional
    public void 회원가입_및_조회테스트() throws Exception {
        //given

        Member member = Member.createMember("유영우", new Address("수원시", "498", "16708"));

        Member member2 = Member.createMember("홍길동", new Address("한양","1234", "1212"));

        //when
        memberService.join(member);
        memberService.join(member2);

        Member findMember = memberService.findOne(member.getId());
        List<Member> members = memberService.findMembers();
        //then
        Assert.assertEquals(member, findMember);
        Assert.assertEquals(2, members.size());
    }


    @Transactional
    @Test(expected = IllegalStateException.class)
    public void 중복회원테스트() throws Exception {
        //given
        Member member = Member.createMember("유영우", new Address("수원시", "498", "16708"));

        Member member2 = Member.createMember("유영우", new Address("수원시", "498", "16708"));
        //when
        memberService.join(member);
        memberService.join(member2);

        //then
        Assert.fail("중복 회원 가입 예외가 발생하지 않았습니다.");
    }

}