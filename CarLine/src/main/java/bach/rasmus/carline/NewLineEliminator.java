package bach.rasmus.carline;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class NewLineEliminator implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);

        String cleanedBody = body.replaceAll("\\r?\\n", "");
        exchange.getIn().setBody(cleanedBody);
    }
}
