package yoo.study.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    private final WebService webService;
    private final ObjectProvider<MyLogger> myLoggerProvider;

    @Autowired
    public WebController(WebService webService, ObjectProvider<MyLogger> myLoggerProvider) {
        this.webService = webService;
        this.myLoggerProvider = myLoggerProvider;
    }

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(request.getRequestURL().toString());

        myLogger.log("controller test");
        webService.logic("Test Id");
        return "OK";
    }
}
