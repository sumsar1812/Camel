package bach.rasmus.carline;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class DefaultAggregationStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            System.out.println("First exchange with body: " + newExchange.getIn().getBody(String.class));
            return newExchange;
        }

        String oldBody = oldExchange.getIn().getBody(String.class).replaceAll("\\n", "");
        String newBody = newExchange.getIn().getBody(String.class).replaceAll("\\n", "");




        String combinedBody = oldBody + " " + newBody;
        oldExchange.getIn().setBody(combinedBody, String.class);

        return oldExchange;
    }

}
