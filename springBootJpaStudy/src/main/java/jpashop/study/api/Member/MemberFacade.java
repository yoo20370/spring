package jpashop.study.api.Member;

import jpashop.study.domain.Address;
import jpashop.study.domain.Member;
import jpashop.study.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.eclipse.jdt.internal.compiler.lookup.MemberTypeBinding;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFacade {

    private final MemberService memberService;

    @Transactional
    public Member saveMemberV1(Member member) {
        Long saveMemberId = memberService.join(member);
        return memberService.findOne(saveMemberId);
    }

    @Transactional
    public Result<CreateMemberResponse> saveMemberV2(CreateMemberRequest request) {
        Member member = Member.createMember(request.getName(), new Address("서울", "강남로", "10101"));
        Long saveMemberId = memberService.join(member);

        // Command와 Query를 분리하기 위함
        Member findMember = memberService.findOne(saveMemberId);
        String message = findMember.getName() + "님 가입을 환영합니다.";
        return new Result<CreateMemberResponse>(message, new CreateMemberResponse(findMember.getId(), findMember.getName(), findMember.getAddress()));
    }

    @Transactional
    public Result<UpdateMemberResponse> updateMember(Long memberId, UpdateMemberRequest request) {
        // 준영속 객체를 영속화 시킨다.
        Member findMember = memberService.findOne(memberId);
        findMember.changeName(request.getName());
        return new Result<UpdateMemberResponse>("변경되었습니다.", new UpdateMemberResponse(findMember.getId(), findMember.getName(), findMember.getAddress()));
    }

    public Result<List<MemberDto>> membersV2() {
        List<Member> members = memberService.findMembers();

        List<MemberDto> collect = members.stream()
                .map(m -> new MemberDto(m.getId(), m.getName(), m.getAddress()))
                .collect(Collectors.toList());

        return new Result<List<MemberDto>>("모든 회원 조회", collect);
    }
}
