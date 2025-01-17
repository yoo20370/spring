package yoo.study.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Scope("prototype")
    static class ProtoTypeBean {

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
