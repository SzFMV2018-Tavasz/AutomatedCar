package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import java.util.ArrayList;
import java.util.Arrays;

public final class CarSpecifications {

    private int maxPRM = 7400;
    private int idleRPM = 740;
    private int maxSpeed = 190;    // unit: km/h
    private int engineTorque = 180;
    private int engineBrakeTorque = 35;
    private int weight = 1360;
    private double maxBrakeSpeed = 6.12;
    private int gearboxMaxLevel = 6;
    private int gearboxMinLevel = 1;

    /**
     * Gears: R, 1, 2, 3, 4, 5, 6
     */
    private ArrayList<Double> gearRatios =
            new ArrayList<>(Arrays.asList(2.90, 2.66, 1.78, 1.30, 1.00, 0.74, 0.50));

    private ArrayList<Integer> gearShiftLevelSpeed =
            new ArrayList<>(Arrays.asList(15, 30, 50, 70, 100, 125 ));

    public int getMaxPRM() {
        return maxPRM;
    }

    public int getIdleRPM() {
        return idleRPM;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getEngineTorque() {
        return engineTorque;
    }

    public ArrayList<Double> getGearRatios() {
        return gearRatios;
    }

    public int getWeight() {
        return weight;
    }

    public int getEngineBrakeTorque() {
        return engineBrakeTorque;
    }

    public double getMaxBrakeSpeed() {
        return maxBrakeSpeed;
    }

    public ArrayList<Integer> getGearShiftLevelSpeed() {
        return gearShiftLevelSpeed;
    }

    public int getGearboxMaxLevel() {
        return gearboxMaxLevel;
    }

    public int getGearboxMinLevel() {
        return gearboxMinLevel;
    }
}
