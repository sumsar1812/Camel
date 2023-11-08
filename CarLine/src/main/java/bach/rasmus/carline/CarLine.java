package bach.rasmus.carline;

import javax.jms.ConnectionFactory;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.qpid.jms.JmsConnectionFactory;
import bach.rasmus.shared.Car;
import bach.rasmus.shared.CarColor;
import bach.rasmus.shared.Utils;


public class CarLine {
    public static void main(String[] args) {

        CarLine carLine = new CarLine();

        carLine.run();
    }

    private void run() {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            ConnectionFactory connectionFactory = new JmsConnectionFactory("amqp://localhost:5672");

            JmsComponent jmsComponent = AMQPComponent.jmsComponentAutoAcknowledge(connectionFactory);
            jmsComponent.setUsername("guest");
            jmsComponent.setPassword("guest");
            camelContext.addComponent("amqp", jmsComponent);
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    onException(Exception.class)
                            .process(exchange -> {
                                Exception cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
                                System.out.println("Exception caught during route execution: " + cause.getMessage());
                            })
                            .log(LoggingLevel.ERROR, "Error processing message: ${body}")
                            .handled(true);

                    from("file:data/input?noop=true")
                            .split()
                            .tokenize("\r?\n", true)
                            .process(exchange -> makeCar(exchange))
                            .to("amqp:queue:carQueue");


                }
            });
            camelContext.start();
            Thread.sleep(50000);

            camelContext.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeCar(Exchange exchange) throws InterruptedException {
        String[] bodySplit = exchange.getIn().getBody(String.class).split(":");
        if (bodySplit.length <= 1) {
            throw new RuntimeException("Not formatted correctly");
        }
        String colorString = bodySplit[0];
        String lengthString = bodySplit[1];
        int length = Utils.checkInteger(lengthString).orElseThrow(() -> new IllegalArgumentException ("Length not formatted correctly: " + lengthString));
        CarColor color = CarColor.getByName(colorString).orElseThrow(() -> new IllegalArgumentException ("Color not found: " + colorString));
        Car car = new Car(color, length);
        System.out.println("Car entered the car line!");
        Thread.sleep(500);

        exchange.getIn().setBody(car);
    }
}
