package one.contentbox.boxd.spring.actuate;

import one.contentbox.boxd.protocol.BoxdClient;
import one.contentbox.boxd.protocol.core.response.NetworkID;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.util.Assert;

/**
 * Health check indicator for boxd client
 */
public class BoxdClientHealthIndicator extends AbstractHealthIndicator {

    private BoxdClient boxdClient;

    public BoxdClientHealthIndicator(BoxdClient boxdClient){
        Assert.notNull(boxdClient, "BoxdClient must not be null");
        this.boxdClient = boxdClient;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            NetworkID networkID = boxdClient.getNetworkId();
            if(networkID.getId() > 0
                    && ("testnet".equals(networkID.getLiteral()) || "mainnet".equals(networkID.getLiteral())) ){
                builder.up();

                int blockHeight = boxdClient.getBlockHeight();
            }else {
                builder.down();
            }
        } catch (Exception e){
            builder.down(e);
        }
    }
}
