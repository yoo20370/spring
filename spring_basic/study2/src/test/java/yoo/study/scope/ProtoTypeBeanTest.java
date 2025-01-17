package yoo.study.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

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

    @Test
    @DisplayName("스프링이 제공하는 ObjectProvider를 사용하면 실제 객체가 필요할 때 DL을 수행하여 사용 가능")
    void protoTypeFind2() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonScopeBean.class, ProtoTypeBean.class);

        SingletonScopeBean singletonScopeBean1 = ac.getBean(SingletonScopeBean.class);
        SingletonScopeBean singletonScopeBean2 = ac.getBean(SingletonScopeBean.class);

        int count1 = singletonScopeBean1.logic();
        System.out.println("count1 = " + count1);
        assertThat(count1).isEqualTo(1);

        int count2 = singletonScopeBean2.logic();
        System.out.println("count2 = " + count2);
        assertThat(count2).isEqualTo(1);

        assertThat(singletonScopeBean1.getProtoTypeBean()).isNotSameAs(singletonScopeBean2.getProtoTypeBean());
    }

    @Scope("singleton")
    static class SingletonScopeBean {

        // ObjectProvider를 사용하면 싱글톤 빈을 등록할 때, 프로토타입 빈 대신 ObjcetProvider 객체를 주입
        // 실제 프로토타입 빈에 접근할 때 스프링 컨테아너에서 조회하여 가져온다.(DL)
        // 이 때 프로토타입 빈의 경우 새로운 객체를 생성하고 의존성 주입 받은 후 반환된다.
        private final ObjectProvider<ProtoTypeBean> protoTypeBeanProvider;

        @Autowired
        public SingletonScopeBean(ObjectProvider<ProtoTypeBean> protoTypeBeanProvider) {
            this.protoTypeBeanProvider = protoTypeBeanProvider;
        }

        public int logic() {
            ProtoTypeBean protoTypeBean = protoTypeBeanProvider.getObject();
            protoTypeBean.addCount();
            return protoTypeBean.getCount();
        }

        public ProtoTypeBean getProtoTypeBean() {
            ProtoTypeBean protoTypeBean = protoTypeBeanProvider.getObject();
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
