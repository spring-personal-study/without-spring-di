package hello.core.lifecycle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);

        assertThat(client.getUrl()).isEqualTo("http://hello-spring.dev");

        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        /*
            instead of "NetworkClient implements InitializingBean, DisposableBean", use this.
            @Bean(initMethod = ...) uses meta configure data, so can apply initializing/disposal method to external lib that cannot modify.

            default destroyMethod is "(inferred)". : destroyMethod = "(inferred)"
            what for (inferred)?

            if annotation is @Bean(initMethod = "init") and end,
            destroyMethod searches NetworkClient class if there's naming "close" or "shutdown".
            if this method existed, destroyMethod calls automatically.

            and if you don't want to use this "searching", you do type -> @Bean(initMethod = "init", destroyMethod = "")
        */
        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
