package yoo.study.beanfind;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import yoo.study.AutoAppConfig;
import yoo.study.discount.DiscountPolicy;
import yoo.study.member.Grade;
import yoo.study.member.Member;
import yoo.study.member.MemberService;

import java.util.HashMap;
import java.util.Map;

public class AllBeanTest {

    @Test
    @DisplayName("조회한 빈이 모두 필요할 때 - Map")
    void findAllBeanByType() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DiscountService.class, AutoAppConfig.class);
        Member member = new Member(1L, "memberA", Grade.VIP);

        DiscountService discountService = ac.getBean(DiscountService.class);
        int discountPrice = discountService.calDiscountPrice(member, 30000, "rateDiscountPolicy");

        Assertions.assertThat(discountPrice).isEqualTo(3000);
    }
    static class DiscountService {

        private final Map<String, DiscountPolicy> policyMap;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap) {
            System.out.println("policyMap = " + policyMap);
            this.policyMap = policyMap;
        }

        public int calDiscountPrice(Member member, int price, String discountPolicyName) {
            DiscountPolicy discountPolicy = policyMap.get(discountPolicyName);
            return discountPolicy.calculationDiscountPrice(member, price);
        }
    }
}
