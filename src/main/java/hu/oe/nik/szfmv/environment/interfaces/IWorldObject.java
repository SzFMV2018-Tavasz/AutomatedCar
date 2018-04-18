package hu.oe.nik.szfmv.environment.interfaces;

import java.awt.geom.Point2D;

/**
 * Interface for objects that can be Visualized
 * Marks the functionality of the subclasses
 */
public interface IWorldObject {

    /**
     * @return Return with the world object rotation value
     */
    double getRotation();

    /**
     * @return Return with the world object image file name
     */
    String getImageFileName();

    /**
     * @return Return with the the world object location point
     */
    Point2D getLocation();
}
