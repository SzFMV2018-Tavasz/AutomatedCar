package hu.oe.nik.szfmv.environment;

import java.awt.*;

/**
 * Interface for objects that can be Visualized
 * Marks the functionality of the subclasses
 *
 */
public interface IWorldObject {

    Point getLocation();
    double getRotation();
    String getType();
}
