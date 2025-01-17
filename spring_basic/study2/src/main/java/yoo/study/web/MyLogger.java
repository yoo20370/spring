package yoo.study.web;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class MyLogger {

    private String uuid;
    private String requestURL;

    public MyLogger() {
        this.uuid = UUID.randomUUID().toString();
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("["+ uuid + "]" + "[" + requestURL +"] " + message);
    }

    @PostConstruct
    public void init() {
        System.out.println("["+ uuid + "]" + "request scope bean create");
    }

    @PreDestroy
    public void close() {
        System.out.println("["+ uuid + "]" + "request scope bean close");
    }
}
