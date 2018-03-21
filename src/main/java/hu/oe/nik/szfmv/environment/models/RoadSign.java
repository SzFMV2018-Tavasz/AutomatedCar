package hu.oe.nik.szfmv.environment.models;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoadSign extends Stationary {
    private static double CIRCLEDIAMETER = 3;

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public RoadSign(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    /**
     * Creates an object with default parameter values.
     */
    public RoadSign() {
        super(0, 0, null);
    }


    @Override
    public void generateShape() {
        //Circle radius for shape property
        //For more information please see Issue #221
        this.shape = (Shape) new Ellipse2D.Double(
                this.getX() + this.getWidth() / 2,
                this.getY() + this.getHeight() / 2,
                CIRCLEDIAMETER, CIRCLEDIAMETER);
    }
}
