package hello.spring.web;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class MyLogger {

    private UUID uuid;
    private String requestUrl;

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String log() {
        return "[" +this.uuid+ "]" + "[" + this.requestUrl + "]";
    }

    @PostConstruct
    public void init() {
        this.uuid = UUID.randomUUID();
        System.out.println("[" +this.uuid+ "]" + " request scope bean create");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("[" +this.uuid+ "]" + " request scope bean close");
    }
}
