package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        // 모델이란 데이터를 실어서 뷰로 넘길 수 있다.
        model.addAttribute("data", "hello!!!");

        // 뷰 이름을 반환해야 함
        return "hello";
    }
}
