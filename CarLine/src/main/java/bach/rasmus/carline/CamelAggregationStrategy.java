package bach.rasmus.carline;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class CamelAggregationStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            return newExchange;
        }
        String oldBody = oldExchange.getIn().getBody(String.class);
        String newBody = newExchange.getIn().getBody(String.class);


        String body = oldBody.replace("\n", "") + " -- " + newBody.replace("\n", "");
        oldExchange.getIn().setBody(body);
        return oldExchange;
    }
}
