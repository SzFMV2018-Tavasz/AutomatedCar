package hu.oe.nik.szfmv.environment;

public abstract class WorldObject implements IWorldObject{
    protected Point location;
    protected double rotation = 0f;
    protected String type; //this is equals with the image name

    //widt and height for what?
    protected int width;
    protected int height;

    public WorldObject(Point location, String type, double rotation) {
        this.location=location;
        this.type = type;
        this.rotation = rotation;
    }

    public Point getLocation() {
        return this.location;
    }

    public double getRotation() {
        return this.rotation;
    }

    public String getType() {
        return this.type;
    }

    //--------------------------------------------------------------------------
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}