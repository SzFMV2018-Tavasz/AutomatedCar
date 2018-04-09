package hu.oe.nik.szfmv.environment;

import hu.oe.nik.szfmv.environment.interfaces.IWorldObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class WorldObject implements IWorldObject {

    protected int width = 10;
    protected int height = 10;
    protected double rotation = 0f;
    protected String imageFileName;
    protected Point location;
    protected Point offsetVector;
    protected Shape shape;

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public WorldObject(int x, int y, String imageFileName) {
        this.location = new Point(x, y);
        this.imageFileName = imageFileName;
    }

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param location      the initial location coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getRotation() {
        return this.rotation;
    }

    public String getImageFileName() {
        return this.imageFileName;
    }


    /**
     * This method returns the actual shape of the WorldObject
     */
    public Shape getShape() {
        generateShape();
        return this.shape;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    /**
     * This method get and store attribute from imageFile
     *
     * @throws IOException when image not found
     */
    public void generateDimens() throws IOException {
        BufferedImage image = ImageIO.read(
                new File(
                        ClassLoader.getSystemResource(this.getImageFileName())
                                .getFile()));
        width = image.getWidth();
        height = image.getHeight();
    }

    /**
     * This method create Rectangle
     */
    public void generateShape() {
        AffineTransform tx = new AffineTransform();
        tx.rotate(this.getRotation(), this.getX(), this.getY());
        this.shape = tx.createTransformedShape(
                new Rectangle(
                        this.getX(),
                        this.getY(),
                        this.getWidth(),
                        this.getHeight()
                )
        );
    }

    @Override
    public String toString() {
        return "WorldObject{" +
                "width=" + width +
                ", height=" + height +
                ", rotation=" + rotation +
                ", imageFileName='" + imageFileName + '\'' +
                ", location=" + location +
                ", offsetVector=" + offsetVector +
                ", shape=" + shape +
                '}';
    }

}

