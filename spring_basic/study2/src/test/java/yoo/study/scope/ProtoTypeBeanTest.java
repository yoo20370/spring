package yoo.study.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import yoo.study.singleton.SingletonBean;

import static org.assertj.core.api.Assertions.assertThat;

public class ProtoTypeBeanTest {


    @Test
    @DisplayName("프로토타입 스코프 빈의 경우 스프링 컨테이너에 조회할 때마다 새로운 객체를 생성해서 반환한다.")
    void protoTypeBeanTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class);

        ProtoTypeBean protoTypeBean1 = ac.getBean(ProtoTypeBean.class);
        ProtoTypeBean protoTypeBean2 = ac.getBean(ProtoTypeBean.class);
        ProtoTypeBean protoTypeBean3 = ac.getBean(ProtoTypeBean.class);

        System.out.println("protoTypeBean1 = " + protoTypeBean1);
        System.out.println("protoTypeBean2 = " + protoTypeBean2);
        System.out.println("protoTypeBean3 = " + protoTypeBean3);

        assertThat(protoTypeBean1).isNotSameAs(protoTypeBean2);
        assertThat(protoTypeBean1).isNotSameAs(protoTypeBean3);
        assertThat(protoTypeBean2).isNotSameAs(protoTypeBean3);
    }

    @Test
    @DisplayName("싱글톤 스코프 빈 안의 프로토타입 빈의 경우 의존성 주입 때 생성된 객체이다.")
    void protoTypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonScopeBean.class, ProtoTypeBean.class);

        SingletonScopeBean singletonScopeBean1 = ac.getBean(SingletonScopeBean.class);
        SingletonScopeBean singletonScopeBean2 = ac.getBean(SingletonScopeBean.class);

        int count1 = singletonScopeBean1.logic();
        System.out.println("count1 = " + count1);
        assertThat(count1).isEqualTo(1);

        int count2 = singletonScopeBean2.logic();
        System.out.println("count2 = " + count2);
        assertThat(count2).isEqualTo(2);

        assertThat(singletonScopeBean1.getProtoTypeBean()).isSameAs(singletonScopeBean2.getProtoTypeBean());
    }

    @Scope("singleton")
    static class SingletonScopeBean {

        // 해당 객체는 싱글톤 객체가 생성될 때 스프링 컨테이너가 생성 및 DI 한 후 반환해주는 객체
        // 프로토타입 빈으로 컨테이너를 조회할 때마다 새로 생성되는 것, 지금은 싱글톤 빈이 컨테이너에 있는지 조회하는 경우임
        // SingletonScopeBean 해당 프로토타입 스코프 빈의 참조값을 가지고 있음
        private final ProtoTypeBean protoTypeBean;

        @Autowired
        public SingletonScopeBean(ProtoTypeBean protoTypeBean) {
            this.protoTypeBean = protoTypeBean;
        }

        public int logic() {
            protoTypeBean.addCount();
            return protoTypeBean.getCount();
        }

        public ProtoTypeBean getProtoTypeBean() {
            return protoTypeBean;
        }

        @PostConstruct
        public void init() {

        }

        @PreDestroy
        public void close() {

        }
    }

    @Scope("prototype")
    static class ProtoTypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("ProtoTypeBean.init");
        }

        // 스프링 컨테이너가 관리하지 않기 때문에 호출되지 않음
        // 즉, 생성 및 의존관계 주입 이후, 클라이언트가 프로토타입 빈 관리해야 함
        @PreDestroy
        public void destroy() {
            System.out.println("ProtoTypeBean.destroy");
        }

    }
}
