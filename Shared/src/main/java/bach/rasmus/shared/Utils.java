package bach.rasmus.shared;

import java.util.Optional;

public class Utils {

    public static Optional<Integer> checkInteger(String s) {
        return s.matches("-?\\d+$") ? Optional.of(Integer.parseInt(s)) : Optional.empty();
    }

}
