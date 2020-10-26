package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//implements InitializingBean, DisposableBean is supported by spring framework only. (you can't use these in other configuration.)
//and cannot be applied to external lib that dev cannot modified. (so rarely uses in these days.)
//@PostConstruct @PreDestroy annotation follows jsr-250 java canonical rule, so can use anywhere. but it also cannot be applied to external lib.
//recentest spring strongly recommends use @PostConstruct & @PreDestroy, but when work with external lib, use @Bean(initMethod = "init", destroyMethod = "close")
public class NetworkClient {
    private String url;

    public NetworkClient() {
        System.out.println("NetworkClient.NetworkClient url =" + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + ", message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("disconnect: " + url);
    }

    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
