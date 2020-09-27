package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SingleTonTest {

    @Test
    @DisplayName("pur DI container without Spring framework function")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        //1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        //2. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        // 참조값이 다름
        System.out.println(memberService1);
        System.out.println(memberService2);

        //memberService1 != memberService2
        Assertions.assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("use object that applying singleton pattern")
    void singletonServiceTest() {
        SingletonService service = SingletonService.getInstance();
        SingletonService service2 = SingletonService.getInstance();

        System.out.println(service);
        System.out.println(service2);

        Assertions.assertThat(service).isSameAs(service2);
        //isSameAs => reference address compare. is same instance address.
        //isEqualTo =>  value compare. is equal value.
    }

    @Test
    @DisplayName("use object applying Spring Container")
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        //memberService1 == memberService2
        Assertions.assertThat(memberService1).isSameAs(memberService2);

    }
}
