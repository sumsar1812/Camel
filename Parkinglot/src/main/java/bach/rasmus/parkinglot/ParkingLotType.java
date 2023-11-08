package bach.rasmus.parkinglot;

import java.util.Arrays;
import java.util.Optional;

public enum ParkingLotType {
    LARGE,
    OTHER;

    public static Optional<ParkingLotType> getByName(String name) {
        return Arrays.stream(ParkingLotType.values()).filter(i -> i.name().equalsIgnoreCase(name)).findFirst();
    }

}
