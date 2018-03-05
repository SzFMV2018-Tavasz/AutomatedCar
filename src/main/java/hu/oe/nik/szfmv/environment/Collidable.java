package hu.oe.nik.szfmv.environment;

public abstract class Collidable extends WorldObject{

    public Collidable(Point location, String type, double rotation) {
        super(location, type, rotation);
    }
}
