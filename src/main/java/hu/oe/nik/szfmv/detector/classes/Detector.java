package hu.oe.nik.szfmv.detector.classes;

import hu.oe.nik.szfmv.detector.interfaces.ICamera;
import hu.oe.nik.szfmv.detector.interfaces.IRadarUltrasonic;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.Collidable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Responsible for Sensor Functionality
 */
public final class Detector implements ICamera, IRadarUltrasonic {

    private List<WorldObject> worldObjects;
    private static Detector detector;

    private Detector() {}

    public void setWorldObjects(List<WorldObject> worldObjects) {
        this.worldObjects = worldObjects;
    }

    public static Detector getDetector() {
        if(detector != null) {
            return detector;
        }
        else {
            return detector = new Detector();
        }
    }

    @Override
    public List<WorldObject> getWorldObjects(Point a, Point b, Point c) {
        List<WorldObject> noticeableObjects = new ArrayList<WorldObject>();

        return noticeableObjects;
    }

    @Override
    public List<Collidable> getCollidableObjects(Point a, Point b, Point c) {
        List<Collidable> noticeableObjects = new ArrayList<Collidable>();

        return noticeableObjects;
    }
}
