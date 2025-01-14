package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @Component 애너테이션이 붙은 클래스를 찾아서 자동으로 스프링 빈으로 등록
@ComponentScan(

//        basePackages = "hello.core",
        //basePackageClasses = AutoAppConfig.class,
        // @Configuration 내부에 @Component 애너테이션이 들어있다. 그러므로 컴포넌트 스캔시 스프링 빈으로 자동 등록됨
        // 컴포넌트 스캔으로 검색하는데 스프링 빈으로 등록하지 않을 것을 설정 (여기서는 @Configuration이 붙은 클래스를 제외)
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // -> Configuration 애너테이션이 붙은 클래스를 제외하겠다는 필터
)
public class AutoAppConfig {

//    @Bean(name = "memoryMemberRepository")
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }

}
