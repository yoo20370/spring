//package hello.spring.callback;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//public class CallBackMethodTest {
//
//    @Test
//    void helloTest() {
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(HelloA.class, HelloB.class);
//    }
//
//    static class HelloA implements InitializingBean, DisposableBean {
//
//        private final HelloB helloB;
//
//        @Autowired
//        public HelloA(HelloB helloB) {
//            this.helloB = helloB;
//        }
//
//        @Override
//        public void destroy() throws Exception {
//            System.out.println("helloA.destroy call !!!!");
//        }
//
//        @Override
//        public void afterPropertiesSet() throws Exception {
//            System.out.println("helloA.afterPropertiesSet call !!!!");
//        }
//    }
//
//    static class HelloB {
//
//        @PostConstruct
//        void hello() {
//            System.out.println("hello !!!!");
//        }
//
//        @PreDestroy
//        void bye() {
//            System.out.println("bye !!!!");
//        }
//
//    }
//
//
//}
//
//
//
