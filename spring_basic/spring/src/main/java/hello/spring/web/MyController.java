package hello.spring.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MyController {

    private final MyLogger myLogger;
    private final MyService myService;

    @GetMapping("/log-demo")
    @ResponseBody
    public void log_demo(HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        myLogger.setRequestUrl(requestUrl);
        System.out.println(myLogger.log()+" Controller test");
        myService.test("testId");
    }
}
