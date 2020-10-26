package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.count).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.count).isEqualTo(1);
        ac.close();
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(2); // test pass. because final prototypeBean in ClientBean was not created twice. use provider.
    }

    @Test
    void singletonClientUsePrototypeWithObjectProvider() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBeanWithObjectProvider.class, PrototypeBean.class);
        ClientBeanWithObjectProvider clientBean1 = ac.getBean(ClientBeanWithObjectProvider.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBeanWithObjectProvider clientBean2 = ac.getBean(ClientBeanWithObjectProvider.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototypeWithProvider() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBeanWithProvider.class, PrototypeBean.class);
        ClientBeanWithProvider clientBean1 = ac.getBean(ClientBeanWithProvider.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBeanWithProvider clientBean2 = ac.getBean(ClientBeanWithProvider.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    @Component
    static class ClientBean {
        private final PrototypeBean prototypeBean;

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    @Component
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }

    // ----- provider
    @Scope("singleton")
    @Component
    static class ClientBeanWithObjectProvider {

        //ObjectProvider is supported by spring framework only. provides more functions.
        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanObjectProvider.getObject();
            PrototypeBean otherPrototypeBean = prototypeBeanObjectProvider.getObject();
            assert !prototypeBean.equals(otherPrototypeBean); // different
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("singleton")
    @Component
    static class ClientBeanWithProvider {

        //Provider is supported by java canonical rule. more simple, easy to work with Mock / Unit test.
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            PrototypeBean otherPrototypeBean = prototypeBeanProvider.get();
            assert !prototypeBean.equals(otherPrototypeBean); // different
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }
}
