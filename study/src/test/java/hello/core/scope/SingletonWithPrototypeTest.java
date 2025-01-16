package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class SingletonWithPrototypeTest {

    @Test
    void singletonBeanWithProtoTypeBean() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class, ProtoTypeBean.class);

        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        int count1 = singletonBean1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
        int count2 = singletonBean2.logic();
        Assertions.assertThat(count2).isEqualTo(1);

        ac.close();
    }

    @Scope("singleton")
    static class SingletonBean {

        private final ObjectProvider<ProtoTypeBean> protoTypeBeanProvider;

        @Autowired
        public SingletonBean(ObjectProvider<ProtoTypeBean> protoTypeBeanProvider) {
            this.protoTypeBeanProvider = protoTypeBeanProvider;
        }

        public int logic() {
            ProtoTypeBean protoTypeBean = protoTypeBeanProvider.getObject();
            protoTypeBean.addCount();
            return protoTypeBean.getCount();
        }

        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void close() {
            System.out.println("SingletonBean.close");
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

        @PreDestroy
        public void close() {
            System.out.println("ProtoTypeBean.close");
        }
    }
}
