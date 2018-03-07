package hu.oe.nik.szfmv.environment.interfaces;

import java.awt.*;

/**
 * Interface for objects that can be Visualized
 * Marks the functionality of the subclasses
 */
public interface IWorldObject {

    float getRotation();

    String getImageFileName();

    Point getLocation();
}
