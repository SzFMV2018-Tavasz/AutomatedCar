package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.car.CarPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection.RoadSignDetectionPacket;
import hu.oe.nik.szfmv.common.Utils;
import hu.oe.nik.szfmv.detector.classes.Triangle;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.RoadSign;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

/**
 * Detects the closest roadsign to the car
 */
public class RoadSignDetection extends SystemComponent {

    private static final double CAMERAANGLEOFVIEW = 60; // degree
    private static final double CAMERARANGE = 10 * 50; // m * pixels/m
    private RoadSignDetectionPacket roadSignDetectionPacket;

    /**
     * @param virtualFunctionBus VirtualFunctionBus parameter
     */
    public RoadSignDetection(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        roadSignDetectionPacket = RoadSignDetectionPacket.getInstance();
        virtualFunctionBus.roadSignDetectionPacket = this.roadSignDetectionPacket;
    }

    /**
     * makes a triangle representing the camera view on the car, gets the objects which are in the
     * right half in the view of the camera, and returns the closest roadsign object to the position
     * of the camera
     *
     * @return roadsign to show on dashboard
     */
    private RoadSign setRoadSign(CarPacket carPacket) {
        Point cameraPosition = new Point(0, 0);
        cameraPosition.x = carPacket.getX();
        cameraPosition.y = carPacket.getY();
        double cameraRotation = carPacket.getRotation();

        Triangle triangle = new Triangle();
        Point[] trianglePoints = triangle.trianglePoints(cameraPosition, CAMERARANGE, CAMERAANGLEOFVIEW,
                Utils.radianToDegree(-cameraRotation) + 180);
        trianglePoints[2] = calculateMiddlePoint(trianglePoints[1], trianglePoints[2]);
        roadSignDetectionPacket.setTrianglePoints(trianglePoints);
        List<WorldObject> worldObjects;
        worldObjects = World.getDetector().getWorldObjects(trianglePoints[0], trianglePoints[1], trianglePoints[2]);

        List<RoadSign> roadSigns = new ArrayList<>();
        for (int i = 0; i < worldObjects.size(); i++) {
            if (worldObjects.get(i) instanceof RoadSign) {
                roadSigns.add((RoadSign) worldObjects.get(i));
            }
        }
        return findClosestRoadSign(roadSigns, cameraPosition);
    }

    /* ASCII representation

                       /A
                      / |
                     /  |
         camera:    <---C   <== the point we need
                     \  |
                      \ |
                       \B
     */

    /**
     * calculates the middle point between two points
     *
     * @param a one point
     * @param b another point
     * @return the point between the two points
     */
    private Point calculateMiddlePoint(Point a, Point b) {
        Point c = new Point(0, 0);
        double x = (a.getX() + b.getX()) / 2;
        double y = (a.getY() + b.getY()) / 2;
        c.setLocation(x, y);
        return c;
    }

    /**
     * finds the closest roadsign to a specific point from a list of roadsigns
     *
     * @param rs list of roadsigns
     * @param cp a point which is the position of the camera
     * @return roadsign closest to the camera
     */
    private RoadSign findClosestRoadSign(List<RoadSign> rs, Point cp) {
        double actualDistance;
        double minDistance = Integer.MAX_VALUE;
        int pos = 0;
        for (int j = 0; j < rs.size(); j++) {
            actualDistance = cp.distance(findCenterPointOfRoadSign(rs.get(j)));
            if (minDistance > actualDistance) {
                minDistance = actualDistance;
                pos = j;
            }
        }
        if (rs.size() > 0) {
            return rs.get(pos);
        } else {
            return roadSignDetectionPacket.getRoadSignToShowOnDashboard();
        }
    }

    /**
     * finds the center point of a given roadsign
     *
     * @param r the roadsign
     * @return center point
     */
    private Point findCenterPointOfRoadSign(RoadSign r) {
        Point center = new Point(0, 0);
        double x = r.getX() + (r.getWidth() / 2);
        double y = r.getY() + (r.getHeight() / 2);
        center.setLocation(x, y);
        return center;
    }

    @Override
    public void loop() throws MissingPacketException {
        CarPacket carPacket = virtualFunctionBus.carPacket;
        RoadSign roadSign = setRoadSign(carPacket);
        roadSignDetectionPacket.setRoadSignToShowOnDashboard(roadSign);
    }
}
