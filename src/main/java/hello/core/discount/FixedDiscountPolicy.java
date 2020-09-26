package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

public class FixedDiscountPolicy implements DiscountPolicy{
    private final int discountFixAmount = 1000; // 1000원 할인

    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP)
            return discountFixAmount;
        return 0;
    }
}
