package hu.oe.nik.szfmv.environment;

import hu.oe.nik.szfmv.environment.interfaces.IWorldObject;

import java.awt.*;

public abstract class WorldObject implements IWorldObject {

    protected int width;
    protected int height;
    protected float rotation = 0f;
    protected String imageFileName;
    protected Point location;
    protected Point offsetVector;

    /**
     -     * Creates an object of the virtual world on the given coordinates with the given image.
     -     *
     -     * @param x             the initial x coordinate of the object
     -     * @param y             the initial y coordinate of the object
     -     * @param imageFileName the filename of the image representing the object in the virtual world
     -     */
    public WorldObject(int x, int y, String imageFileName) {
        this.location = new Point(x,y);
        this.imageFileName = imageFileName;
    }

    public WorldObject(Point location, String imageFileName) {
        this.location = location;
        this.imageFileName = imageFileName;
    }

    public Point getOffsetVector() {
        return offsetVector;
    }

    public void setOffsetVector(Point offsetVector) {
        this.offsetVector = offsetVector;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getX() {
        return this.location.x;
    }

    public void setX(int x) {
        this.location.x = x;
    }

    public int getY() {
        return this.location.y;
    }

    public void setY(int y) {
        this.location.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public float getRotation() {
        return this.rotation;
    }

    public String getImageFileName() {
        return this.imageFileName;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

}

