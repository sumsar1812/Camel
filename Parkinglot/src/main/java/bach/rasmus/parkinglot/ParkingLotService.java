package bach.rasmus.parkinglot;

import bach.rasmus.shared.Car;
import bach.rasmus.shared.CarColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotService {


    private final Map<ParkingSpotType, List<Car>> carParkingSpots = new HashMap<>();
    private final ParkingLotType parkingLotType;

    public ParkingLotService(ParkingLotType parkingLotType) {
        this.parkingLotType = parkingLotType;
    }


    public ParkingLotType getParkingLotType() {
        return parkingLotType;
    }

    public void parkCar(Car car) {
        car.enter();
        if (car.isColor(CarColor.GREEN)) {
            System.out.println("Parked in green zone in " + parkingLotType.name().toLowerCase() + " type of parking lot!");
            carParkingSpots.computeIfAbsent(ParkingSpotType.GREEN, k -> new ArrayList<>()).add(car);
            return;
        }
        carParkingSpots.computeIfAbsent(ParkingSpotType.OTHER, k -> new ArrayList<>()).add(car);
        System.out.println("Parked in other zone in " + parkingLotType.name().toLowerCase() + " type of parking lot!");
    }

}