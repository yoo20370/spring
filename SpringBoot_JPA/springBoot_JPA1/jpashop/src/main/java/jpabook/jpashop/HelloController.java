package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model) {

        // 스프링 UI에 있는 model에다가 데이터를 실어서 컨트롤러에서 뷰로 넘길 수 있다.
        model.addAttribute("data", "hello!!!");

        // resource/templates/hello
        return "hello";
    }
}
