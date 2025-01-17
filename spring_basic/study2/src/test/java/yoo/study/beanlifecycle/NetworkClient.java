package yoo.study.beanlifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class NetworkClient {
    private String url;

    public NetworkClient() {
        System.out.println("NetworkClient.NetworkClient");

    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void connect(){
        System.out.println("NetworkClient.connect " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    public void disConnect() {
        System.out.println("NetworkClient.disConnect " + url);
    }

    @PostConstruct
    public void init() throws Exception {
        connect();
        call("초기화 연결 메서드");
    }

    @PreDestroy
    public void close() throws Exception {
        disConnect();
    }
}
