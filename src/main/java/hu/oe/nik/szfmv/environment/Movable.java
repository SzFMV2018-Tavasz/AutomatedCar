package hu.oe.nik.szfmv.environment;


//This class will be the parent of the "car" classes.
public abstract class Movable extends Collidable{
    public Movable(Point location, String type, double rotation) {
        super(location, type, rotation);
    }

    public void move(Point newLocation, double newRotation)
    {
        this.location = newLocation;
        this.rotation = newRotation;
    }
}
