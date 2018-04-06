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
public class Detector implements ICamera, IRadarUltrasonic {

    private List<WorldObject> worldObjects;

    public Detector(List<WorldObject> worldObjects) {
        this.worldObjects = worldObjects;
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
