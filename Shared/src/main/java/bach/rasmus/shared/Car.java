package bach.rasmus.shared;

import java.io.Serializable;

public class Car implements Serializable {
    private static final long serialVersionUID = 1L;
    private final CarColor color;
    private final int length;

    public Car(CarColor color, int length) {
        this.color = color;
        this.length = length;
    }


    public void enter() {
        System.out.println("Car with color: " + color + " entered a parking lot");
    }

    public CarColor getColor() {
        return color;
    }

    public boolean isLengthAbove(int carLengthLong) {
        return length >= carLengthLong;
    }

    public boolean isColor(CarColor color) {
        return this.color == color;
    }
}
