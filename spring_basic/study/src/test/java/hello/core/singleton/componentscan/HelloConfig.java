package hello.core.singleton.componentscan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
@ComponentScan (
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyIncludeFilter.class),
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyExcludeFilter.class)
)
public class HelloConfig {

}
