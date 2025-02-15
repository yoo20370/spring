package hello.spring.singleton;

import hello.spring.AppConfig;
import hello.spring.member.MemberRepository;
import hello.spring.order.OrderService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class SingletonTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("동일한 타입을 조회했을 때, 동일한 객체가 반환된다.")
    void singleton() {

        OrderService orderService = ac.getBean(OrderService.class);

        MemberRepository memberRepository1 = ac.getBean(MemberRepository.class);
        MemberRepository memberRepository2 = ac.getBean(MemberRepository.class);
        MemberRepository memberRepository3 = orderService.getMemberRepository();

        System.out.println("memberRepository1 = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository3 = " + memberRepository3);

        Assertions.assertThat(memberRepository1).isSameAs(memberRepository2);
        Assertions.assertThat(memberRepository1).isSameAs(memberRepository3);
    }

    @Test
    void beanScopeTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ScopeAppConfig.class);

        SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        Assertions.assertThat(singletonBean1).isEqualTo(singletonBean2);
        Assertions.assertThat(prototypeBean1).isNotEqualTo(prototypeBean2);

        Assertions.assertThat(singletonBean1.getPrototypeBean()).isEqualTo(singletonBean2.getPrototypeBean());
    }


    static class ScopeAppConfig {
        @Scope("singleton")
        @Bean
        public SingletonBean singletonBean(ObjectProvider<PrototypeBean> prototypeBeanProvider) {
            return new SingletonBean(prototypeBeanProvider);
        }

        @Scope("prototype")
        @Bean(initMethod="init", destroyMethod = "destroy")
        public PrototypeBean prototypeBean() {
            return new PrototypeBean();
        }
    }
    @Getter
    static class SingletonBean {

        private final ObjectProvider<PrototypeBean> prototypeBean;

        @Autowired
        public SingletonBean(ObjectProvider<PrototypeBean> prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SingletonBean.destroy");
        }

    }

    static class PrototypeBean {
        public PrototypeBean() {
        }

        public void init() {
            System.out.println("PrototypeBean.init");
        }

        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
