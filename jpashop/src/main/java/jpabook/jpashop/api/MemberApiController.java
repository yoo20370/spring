package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 문제점
    // 엔티티 자체가 반환되어 불필요한 정보까지 전달 됨, member를 반환하면 orders 정보까지 반환될 수 있음
    // 즉, 모든 정보가 노출될 수 있음
    // @JsonIgnore를 필드에 붙이면 JSON에서 빠지긴한다.
    // API를 위한 기능들이 엔티티로 들어오게 됨
    // 이렇게 되는 경우 엔티티로 의존관계가 들어오는게 아니라 반대로 나가는 것, 양방향 의존관계가 걸리면서 애플리케이션을 수정하기 어려워진다.
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    // 리스트를 그대로 반환하면 JSON 배열 타입으로 바로 나감 "collect" : []
    // 이를 Result로 감싸줌으로써, 해당 데이터 뿐만 아니라 다른 데이터도 묶어서 전송할 수 있게 함 result : { "collect" : [] }
    @GetMapping("/api/v2/members")
    public Result memberV2() {
        List<Member> findMembers= memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }


    // API는 JSON 기준으로 강의에서 할 것
    // 회원을 등록하는 API를 만드는 것, 응답으로 등록된 회원의 id값 반환
    // v1의 유일한 장점은 CreateMemberRequest를 만들지 않아도 된다는 것 - 단점이 더 많음
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        // JSON으로 들어온 데이터를 member에 매핑해서 넣어줄 것 (자동-스프링)
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // 엔티티와 API 스펙 명확하게 분리 - 요청이 들어오고 나갈 때 항상 DTO를 사용해라 (엔티티를 절대 사용하면 안 된다.)
    // 엔티티가 변경되어도 별개이므로 API 스펙이 바뀌지 않는다.
    // 단순히 서버에서 수정만 해주면 된다.
    // 개발자 입장에서 API 문서를 확인하지 않으면 엔티티만 보고 어떤 값이 넘어올지 알 수 없다.
    // DTO를 사용하면 어떤 데이터가 들어올지 DTO만 보면 알 수 있다.
    // 엔티티를 외부에 노출하지 않고 API 스펙에 맞는 DTO를 만드는게 API 만들 때 정석

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @RequestBody @Valid UpdateMemberRequest request,
            @PathVariable("id") Long id) {

        // 커멘드(쓰기)와 쿼리(조회)를 철저히 분리하기 위해서 update메서드에서 Member를 반환하지 않도록 함 (영한님 스타일)
        // update는 커맨드성, Member를 봔환하면 커멘드성과 쿼리성을 동시에 갖게 됨
        memberService.update(id, request.getName());
        Member one = memberService.findOne(id); // 트래픽 많지 않으면 이슈가 되지 않음
        return new UpdateMemberResponse(one.getId(), one.getName());
    }

    @Data
    static class UpdateMemberResponse{
        private Long id;
        private String name;

        public UpdateMemberResponse(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Data
    static class UpdateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
