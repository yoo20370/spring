package hello.spring.discountpolicy;

import hello.spring.member.Grade;
import hello.spring.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RateDiscountPolicy implements DiscountPolicy{

    Integer discountRate = 10;

    @Override
    public int getDiscountPrice(Member member, Integer price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountRate / 100;
        } else {
            return 0;
        }
    }
}
