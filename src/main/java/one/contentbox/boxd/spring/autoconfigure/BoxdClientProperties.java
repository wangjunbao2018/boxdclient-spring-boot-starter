package one.contentbox.boxd.spring.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Boxd client property container
 */

@ConfigurationProperties(prefix = "boxd")
public class BoxdClientProperties {

    public static final String BOXD_PREFIX = "boxd";

    private String host;
    private int port;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
