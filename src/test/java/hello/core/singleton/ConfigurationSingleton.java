package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingleton {

    @Test
    @DisplayName("test @Configuration makes singleton object")
    public void configurationTest() {
        // setup resource
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        // when resource works below.
        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = memberService.getMemberRepository();

        // verify.
        assertThat(memberRepository1).isSameAs(memberRepository2);
        assertThat(memberRepository1).isSameAs(memberRepository);
    }

    @Test
    @DisplayName("")
    public void configurationDeep() throws Exception {
        // setup resource
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig appConfig = ac.getBean("appConfig", AppConfig.class);

        // when resource works below.
        System.out.println("ac.getClass() = " + appConfig.getClass());
        // result: ac.getClass() = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$d39f14fd

        // verify.
        // if use @Configuration,
        // CGLIB(Code Generator Library) makes child class extends AppConfig ->
        // child class gives guarantee the @Bean Object is made by singleton pattern.
    }
}
