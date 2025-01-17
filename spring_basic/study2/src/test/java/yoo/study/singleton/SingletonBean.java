package yoo.study.singleton;

import org.springframework.beans.factory.config.SingletonBeanRegistry;

public class SingletonBean {

    // 클래스 내부에 미리 객체 인스턴스를 생성해서 가지고 있는다.
    private static final SingletonBean instance = new SingletonBean();

    // 클래스 내부에서만 생성자르 호출할 수 있도록 한다.
    private SingletonBean() {

    }

    // 해당 Getter를 통해서만 객체 인스턴스를 꺼낼 수 있다.
    public static SingletonBean getInstance() {
        return instance;
    }
}
