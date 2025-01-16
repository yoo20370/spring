package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac =  new AnnotationConfigApplicationContext(ProtoTypeBean.class);

        System.out.println("find protoTypeBean1");
        ProtoTypeBean protoTypeBean1 = ac.getBean(ProtoTypeBean.class);

        System.out.println("find protoTypeBean2");
        ProtoTypeBean protoTypeBean2 = ac.getBean(ProtoTypeBean.class);

        // 서로 다른 참조값 출력
        System.out.println("protoTypeBean1 = " + protoTypeBean1);
        System.out.println("protoTypeBean2 = " + protoTypeBean2);

        Assertions.assertThat(protoTypeBean1).isNotSameAs(protoTypeBean2);

        // @PreDestroy 가 동작하지 않으므로 클라이언트가 직접 호출해서 처리해야한다.
        protoTypeBean1.destroy();
        protoTypeBean2.destroy();

        ac.close();
    }

    @Scope("prototype")
    static class ProtoTypeBean {

        @PostConstruct
        public void init() {
            System.out.println("ProtoTypeBean.init");
        }

        // 호출되지 않음
        @PreDestroy
        public void destroy() {
            System.out.println("ProtoTypeBean.destroy");
        }
    }
}
