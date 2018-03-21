package hu.oe.nik.szfmv.environment.models;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Tree extends Stationary {

    private static double CIRCLEDIAMETER = 15;

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public Tree(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    /**
     * Creates an object with default parameter values.
     */
    public Tree() {
        super(0, 0, null);
    }

    @Override
    public void generateShape() {
        //For more information please see Issue #221
        this.shape = (Shape) new Ellipse2D.Double(
                this.getX() + this.getWidth() / 2,
                this.getY() + this.getHeight() / 2,
                CIRCLEDIAMETER, CIRCLEDIAMETER);
    }
}
