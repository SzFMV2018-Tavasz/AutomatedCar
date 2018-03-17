package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import java.util.ArrayList;
import java.util.Arrays;

public final class CarSpecifications {

    public static final int MAX_RPM = 7400;       // unit: rpm
    public static final int IDLE_RPM = 740;        // unit: rpm
    public static final double MAX_FORWARD_SPEED = 118.0605;    // unit: m/s - 190 km/h
    public static final double MAX_REVERSE_SPEED = -5.278;    // unit: m/s - 19 km/h
    public static final double MIN_FORWARD_SPEED = 1.3888;     // unit: m/s - 5 km/h
    public static final double MIN_REVERSE_SPEED = -1.3888;     // unit: m/s - -5 km/h
    public static final int ENGINE_BRAKE_TORQUE = 35;         // unit: Nm
    public static final int WEIGHT = 1360;       // unit: kg
    public static final double MAX_BRAKE_SPEED = 6.12;       // unit: m/s - 22 km/h
    public static final int GEARBOX_MAX_LEVEL = 6;
    public static final int GEARBOX_MIN_LEVEL = 1;

    /**
     * Gears: R, 1, 2, 3, 4, 5, 6
     */
    public static final ArrayList<Double> GEAR_RATIOS =
            new ArrayList<>(Arrays.asList(2.90, 2.66, 1.78, 1.30, 1.00, 0.74, 0.50));

    /**
     * 15, 30, 50, 70, 100, 130 km/h
     */
    public static final ArrayList<Double> GEAR_SHIFT_LEVEL_SPEED =
            new ArrayList<>(Arrays.asList(9.3205, 18.6411, 31.0685, 43.4959, 62.1371, 80.7782, Double.MAX_VALUE));
}
