package bach.rasmus.parkinglot;

import org.apache.camel.Endpoint;
import org.apache.camel.support.DefaultComponent;

import java.util.Map;

public class ParkingLotComponent extends DefaultComponent {

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {

        ParkingLotType parkingLotType = ParkingLotType.getByName(remaining)
                .orElseThrow(() -> new RuntimeException("Not valid parking lot type:" + remaining));
        try (Endpoint endpoint = new ParkingLotEndpoint(parkingLotType)) {
            setProperties(endpoint, parameters);
            return endpoint;
        }

    }
}
