package hello.core.scan.filter;

import java.lang.annotation.*;

// Target - 어디에 붙는지 지정할 때 사용, Type이라고 하면 클래스에 붙는 것
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
