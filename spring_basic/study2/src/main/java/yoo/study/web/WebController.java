package yoo.study.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    private final WebService webService;
    private final MyLogger myLogger;

    @Autowired
    public WebController(WebService webService, MyLogger myLogger) {
        this.webService = webService;
        this.myLogger = myLogger;
    }

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        myLogger.setRequestURL(request.getRequestURL().toString());

        myLogger.log("controller test");
        webService.logic("Test Id");
        return "OK";
    }
}
