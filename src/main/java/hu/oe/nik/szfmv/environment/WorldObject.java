package hu.oe.nik.szfmv.environment;

import java.awt.*;

public abstract class WorldObject implements IWorldObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected float rotation = 0f;
    protected String imageFileName;

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public WorldObject(int x, int y, String imageFileName) {
        this.x = x;
        this.y = y;
        this.imageFileName = imageFileName;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    /**
     * Implementation of IWorldObject,
     *
     * @return Point where current location of the WorldObject is
     */
    @Override
    public Point getLocation() {
        return new Point(this.x,this.y);
    }

    /**
     * Implementation of IWorldObject,
     *
     * @return float rotation of the WorldObject compared to baseline 12:00
     */
    public float getRotation() {
        return this.rotation;
    }

    /**
     * Implementation of IWorldObject,
     *
     * @return String location where image of WorldObject is.
     */
    @Override
    public String getType() {
        return this.imageFileName;
    }

    public String getImageFileName() {
        return this.imageFileName;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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