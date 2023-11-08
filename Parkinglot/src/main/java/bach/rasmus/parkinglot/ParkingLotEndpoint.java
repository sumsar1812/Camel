package bach.rasmus.parkinglot;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.support.DefaultConsumer;
import org.apache.camel.support.DefaultEndpoint;

public class ParkingLotEndpoint extends DefaultEndpoint {

    private final ParkingLotType parkingLotType;

    public ParkingLotEndpoint(ParkingLotType parkingLotType) {
        this.parkingLotType = parkingLotType;
    }
    @Override
    protected String createEndpointUri() {
        return "parkingLot://" + parkingLotType.toString();
    }

    @Override
    public Producer createProducer() throws Exception {
        return new ParkingLotProducer(this, new ParkingLotService(parkingLotType));
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new DefaultConsumer(this, processor);
    }
}
