package hu.oe.nik.szfmv;

public class Steering {

    public double getSteerAngle(double wheelPosition)
    {
        // From -60 to 60 degree
        double steerAngle;

        steerAngle = wheelPosition * 0.6;
        return steerAngle;
    }
}
