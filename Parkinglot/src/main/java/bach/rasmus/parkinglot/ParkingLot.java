package bach.rasmus.parkinglot;

import bach.rasmus.shared.Car;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.amqp.AMQPComponent;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.qpid.jms.JmsConnectionFactory;

import javax.jms.ConnectionFactory;

public class ParkingLot {

    private static final int CAR_LENGTH_LONG = 8;

    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.run();
    }

    private void run() {
        try (CamelContext camelContext = new DefaultCamelContext()) {
            ParkingLotComponent parkingLotComponent = new ParkingLotComponent();
            camelContext.addComponent("parkingLot", parkingLotComponent);
            ConnectionFactory connectionFactory = new JmsConnectionFactory("amqp://localhost:5672");

            JmsComponent jmsComponent = AMQPComponent.jmsComponentAutoAcknowledge(connectionFactory);
            jmsComponent.setUsername("guest");
            jmsComponent.setPassword("guest");
            camelContext.addComponent("amqp", jmsComponent);
            camelContext.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("amqp:queue:carQueue")
                            .choice()
                            .when(exchange -> exchange.getIn().getBody(Car.class).isLengthAbove(CAR_LENGTH_LONG))
                            .to("parkingLot:LARGE")
                            .otherwise()
                            .to("parkingLot:OTHER")
                            .endChoice();
                }
            });

            camelContext.start();
            Thread.sleep(50000);
            camelContext.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
