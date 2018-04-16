package hu.oe.nik.szfmv.environment;

import hu.oe.nik.szfmv.environment.interfaces.IWorldObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class WorldObject implements IWorldObject {

    protected int width;
    protected int height;
    protected double rotation = 0f;
    protected String imageFileName;
    protected Point2D location;
    protected Point offsetVector;
    protected Shape shape;

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public WorldObject(double x, double y, String imageFileName) {
        this.location = new Point2D.Double(x, y);
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

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public double getX() {
        return this.location.getX();
    }

    public void setX(double x) {
        this.location = new Point2D.Double(x, this.getY());
    }

    public double getY() {
        return this.location.getY();
    }

    public void setY(double y) {
        this.location = new Point2D.Double(this.getX(), y);
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getRotation() {
        return this.rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public String getImageFileName() {
        return this.imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Shape getShape() {
        return this.shape;
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
        tx.rotate(-this.getRotation(), this.getX() + this.getWidth(), this.getY() + this.getHeight());
        this.shape = tx.createTransformedShape(
                new Rectangle(
                        (int) this.getX(),
                        (int) this.getY(),
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

