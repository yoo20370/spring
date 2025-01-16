package hello.core.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    private final TestService testService;
    private final MyLogger myLogger;


    @Autowired
    public TestController(MyLogger myLogger, TestService testService) {
        this.myLogger = myLogger;
        this.testService = testService;
    }

    @RequestMapping("log-demo")
    @ResponseBody
    public String hello(HttpServletRequest request) {

        String requestUrl = request.getRequestURL().toString();

        myLogger.setUrl(requestUrl);
        myLogger.log("Controller test");
        testService.logic("TestId");
        return "OK";

    }
}
