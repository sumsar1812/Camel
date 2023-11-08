package bach.rasmus.shared;

import java.util.Arrays;
import java.util.Optional;

public enum CarColor {
    BLACK,
    BLUE,
    WHITE,
    RED,
    GREEN,
    YELLOW,
    LIME,
    ORANGE;


    public static Optional<CarColor> getByName(String name) {
        return Arrays.stream(CarColor.values())
                .filter(i -> i.name()
                .equalsIgnoreCase(name))
                .findFirst();
    }


}
