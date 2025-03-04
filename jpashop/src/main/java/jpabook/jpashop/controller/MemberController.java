package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.entity.Address;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        // Model은 컨트롤러에서 뷰로 넘어갈 때 데이터를 model에 저장해서 전달

        // 비어있는 MemberForm이지만, 전달하면 validation 등을 수행해준다.
        // new MemberForm()을 넘겨주지 않으면 뷰에서 추적 불가
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    // @Valid 애너테이션을 사용하면 스프링이 jakarta.validation을 쓰는 것을 인지해서 폼에 있는 @NotEmpty과 같은 validation을 수행해준다.
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        // Member 엔티티를 사용하지 않고 MemberForm을 사용하는 이유
        // -> 확실하게 맞는 것도 아닌 뿐만 아니라 Validation 까지 엔티티에 구현하면 코드가 많이 복잡해지고 더러워진다.
        // 또한 컨틀롤러에서 뷰로 넘어올 때의 validation과 실제 도메인이 원하는 validation이 다를 수도 있음
        // 화면에 핏한 form 데이터를 만들고 데이터를 받는게 맞다. -> 실무 엄청 복잡한데 하나에 몰아넣으면 어려움 발생

        // 폼으로 받고 중간에 데이터를 정제하고 필요한 데이터만 엔테티에 채워서 (리포지토리로) 넘기는게 베스트
        // 엔티티를 파라미터로 받으면, 화면에 의존적으로 바뀌게 됨 즉, 엔티티의 코드가 더러워짐 -> 유지보수 어려워짐
        // JPA를 사용할 때, 엔티티를 최대한 순수하게 유지해야 한다.
        // 쓸데 없는 의존없이 핵심 비즈니스 로직에만 의존성이 있도록 해야한다. - 화면에 맞는 로직을 작성하면 안 된다.
        // 폼 객체나 DTO를 사용해야 한다.

        // 오류 발생 시, 오류가 BindingResult에 담긴다.
        // 이를 활용하여 코드를 실행할 수 있음 (원래는 튕김)

        // BindingResult에 에러가 담겨있을 때, 다시 해당 URL로 보내버린 것, 이 때 스프링이 BindingResult를 뷰에 다 끌고가서 쓸 수 있게 도와준다.
        // 해당 뷰에서 ${#fields.hasErrors('name')}처럼 name에 에러가 있는 경우 특정 작업을 할 수 있도록 사용할 수 있음
        // MemberForm 데이터도 자동으로 뷰로 가져간다.
        // javax.validation 검색해서 사용하면 됨
        // 타임리프 쪽은 타임리프 공식 문서에 thymeleaf + spring 메뉴얼을 찾아보면 된다.
        if (result.hasErrors()) {
            // 스프링이 BindingResult를 뷰까지 끌고가준다.
            return "members/createMemberForm";
        }

        // 더 편하게 작성 가능
        // 현재는 폼에서 데이터를 꺼내서 변환하는 것을 보여주기 위해 이렇게 작성
        Address address =  new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);

        // 재로딩되면 좋지 않아서 redirect를 사용
        // 재로딩하면 중복 요청, POST 요청의 재실행 문제가 발생할 수 있음
        // redirect를 사용하여 새로운 Get요청을 발생시켜서 새로고침해도 회원가입이 다시 실행되자 않도록 함 (Post/Redirect/Get - PRG 패턴)
        // 이로써 입력 정보 초기화, 중복 요청 방지, 명확한 흐름 제공
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        // 실무에서는 Member를 그대로 뷰에 전달하기 보다는 DTO로 변환해서 화면에 꼭 필요한 데이터만 출력하는 것을 권장
        // API를 만들 때는 이유를 불문하고 절대 Entity를 넘기면 안 된다.
        // API는 스펙인데 엔티티를 반환하게 되면 나중에 엔티티 수정이 일어나면 스펙이 변경되는 것 -> 불안정 API 스펙이 됨
        // 템플릿 엔진에서는 선택적으로 사용해도 된다.(지금처럼 진짜 간단하다면)

        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
