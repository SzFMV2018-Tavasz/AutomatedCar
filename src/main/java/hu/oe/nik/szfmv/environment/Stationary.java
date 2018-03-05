package hu.oe.nik.szfmv.environment;

public abstract class Stationary extends Collidable{
    public Stationary(Point location, String type, double rotation) {
        super(location, type, rotation);
    }
}
