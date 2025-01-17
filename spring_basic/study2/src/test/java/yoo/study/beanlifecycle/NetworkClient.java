package yoo.study.beanlifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.UUID;

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

    public void init() throws Exception {
        connect();
        call("초기화 연결 메서드");
    }

    public void close() throws Exception {
        disConnect();
    }
}
