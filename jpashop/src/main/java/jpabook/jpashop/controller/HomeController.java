package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j  // 로그 사용
public class HomeController {

    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        // home.html 파일을 찾아서 데이터 전달 -> SSR
        return "home";
    }
}
