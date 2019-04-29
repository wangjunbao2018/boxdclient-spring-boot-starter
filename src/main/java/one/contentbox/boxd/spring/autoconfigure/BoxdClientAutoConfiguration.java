package one.contentbox.boxd.spring.autoconfigure;

import one.contentbox.boxd.protocol.BoxdClient;
import one.contentbox.boxd.protocol.BoxdDaemon;
import one.contentbox.boxd.protocol.rpc.RpcBoxdClientImpl;
import one.contentbox.boxd.spring.actuate.BoxdClientHealthIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * Boxd client auto configuration for Spring Boot
 */

@Configuration
@ConditionalOnClass(BoxdClient.class)
@EnableConfigurationProperties(BoxdClientProperties.class)
public class BoxdClientAutoConfiguration {

    @Autowired
    private BoxdClientProperties boxdClientProperties;

    @Bean
    @ConditionalOnMissingBean
    public BoxdClient boxdClient(){
        String host = "localhost";
        int port  = 19111;
        if(!StringUtils.isEmpty(boxdClientProperties.getHost())){
            host = boxdClientProperties.getHost();
        }
        if(boxdClientProperties.getPort() > 0){
            port = boxdClientProperties.getPort();
        }

        BoxdClient boxdClient = new RpcBoxdClientImpl(host, port);
        return boxdClient;
    }

    @Bean
    @ConditionalOnMissingBean
    public BoxdDaemon boxdDaemon() {
        String host = "localhost";
        int port  = 19111;
        if(!StringUtils.isEmpty(boxdClientProperties.getHost())){
            host = boxdClientProperties.getHost();
        }
        if(boxdClientProperties.getPort() > 0){
            port = boxdClientProperties.getPort();
        }
        try {
            return new BoxdDaemon(host, port);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Bean
    @ConditionalOnClass(BoxdClient.class)
    BoxdClientHealthIndicator boxdClientHealthIndicator(BoxdClient boxdClient){
        return new BoxdClientHealthIndicator(boxdClient);
    }
}
