package bach.rasmus.parkinglot;

import bach.rasmus.shared.Car;
import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultProducer;

public class ParkingLotProducer extends DefaultProducer {
    private final ParkingLotService parkingLotService;
    private static final int CAR_LENGTH_LONG = 8;
    public ParkingLotProducer(ParkingLotEndpoint endpoint, ParkingLotService parkingLotService) {
        super(endpoint);
        this.parkingLotService = parkingLotService;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        Car car = exchange.getIn().getBody(Car.class);
        if (car.isLengthAbove(CAR_LENGTH_LONG) && parkingLotService.getParkingLotType() != ParkingLotType.LARGE) {
            throw new RuntimeException("This parking lot cant accept long cars!");
        }
        parkingLotService.parkCar(car);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
