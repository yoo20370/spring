package jpashop.study.api.Member;

import jakarta.validation.Valid;
import jpashop.study.api.Result;
import jpashop.study.domain.Member;
import jpashop.study.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberFacade memberFacade;
    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/api/v1/members")
    public Member saveMemberV1(@RequestBody @Valid Member member) {
        return memberFacade.saveMemberV1(member);
    }

    @PostMapping("/api/v2/members")
    public Result<CreateMemberResponse> saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        return memberFacade.saveMemberV2(request);
    }

    // 회원 수정
    @PutMapping("/api/v2/members/{memberId}")
    public Result<UpdateMemberResponse> updateMember(@PathVariable("memberId") Long memberId, @RequestBody @Valid UpdateMemberRequest request) {
        return memberFacade.updateMember(memberId, request);
    }

    @GetMapping("/api/v2/members")
    public Result<List<MemberDto>> membersV2() {
        return memberFacade.membersV2();
    }
}
