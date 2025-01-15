package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
public class LogDemoController {
    // 예외 발생한 이유
    // 컨테이너에서 의존성 주입을 위해 myLogger를 가져오려고 하는데 스프링 빈에 없음
    // request 스코프는 HTTP 요청이 오면 생성되고 끝나면 사라지기 때문

    private final LogDemoService logDemoService;
    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody // 문자열을 응답으로 반환하고 싶은 경우 ResponseBody 사용
    public String logDemo(HttpServletRequest request) {
        // HttpServletRequest - 자바에서 제공하는 표준 서블릿 규약,
        // 이를 통해 고객 request 정보를 받을 수 있다.
        // Provider를 사용했기 때문에 이 시점에 MyLogger 빈이 만들어진다. 그리고 컨테이너에서 가져온다.
        MyLogger myLogger = myLoggerProvider.getObject();

        String requestURL  = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);
        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
