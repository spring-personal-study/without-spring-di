package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    @DisplayName("stateful data object applying singleton pattern")
    void statefulServiceSingleTon() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = new StatefulService();
        StatefulService statefulService2 = new StatefulService();

        // ThreadA : userA orders 10000 won.
        statefulService1.order("userA", 10000);
        // ThreadB : userB orders 10000 won.
        statefulService1.order("userB", 20000);

        int price = statefulService1.getPrice();
        Assertions.assertThat(price).isEqualTo(10000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

    @Test
    @DisplayName("stateful data object applying spring singleton")
    void statefulServiceSpringSingleTon() {

    }
}