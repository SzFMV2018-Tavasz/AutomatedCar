package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.detector.RadarSensorPacket;
import hu.oe.nik.szfmv.detector.classes.Detector;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.Collidable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FrontBackDetector extends SystemComponent {

    private List<Collidable> previousCollidables;

    private RadarSensorPacket radar;

    private List<WorldObject> objects;

    private Detector det;

    /**
     * @param worldobjects Objects from world lol
     * @param vfb          virtualfunctionbus
     */
    public FrontBackDetector(VirtualFunctionBus vfb, List<WorldObject> worldobjects) {
        super(vfb);
        objects = worldobjects;
        previousCollidables = new ArrayList<>();
        radar = RadarSensorPacket.getInstance();
        det = Detector.getDetector();
    }

    public List<Collidable> getPreviousCollidables() {
        return previousCollidables;
    }

    /**
     * @param traingle points of the traingle
     * @return center line of the traingle
     */
    private Point[] centerLineofTraingle(Point[] traingle) {
        Point[] centerline = new Point[2];
        centerline[0] = traingle[0];
        Point c = new Point();
        c.x = (traingle[1].x + traingle[2].x) / 2;
        c.y = (traingle[1].y + traingle[2].y) / 2;
        centerline[1] = c;
        return centerline;
    }

    /**
     * @param centerLine                  Centerline of triangle
     * @param collidableObjectsInTriangle objects that are collidable in triangle
     * @return returns collidable objects in the triangle that are closer to the centerline
     */
    private List<Collidable> getCollidableObjectsApproachingCenterLine(Point[] centerLine,
                                                                       List<Collidable> collidableObjectsInTriangle) {

        List<Collidable> approachingCollidables = new ArrayList<Collidable>();


        for (Collidable object : collidableObjectsInTriangle) {

            Collidable previousCollidable = null;
            for (Collidable previousobject : previousCollidables) {
                if (previousobject.getImageFileName().equals(object.getImageFileName())) {
                    previousCollidable = previousobject;
                    break;
                }
            }

            Point objloc = new Point();
            objloc.x = (int) object.getX();
            objloc.y = (int) object.getY();

            Point prevcol = new Point();
            if (previousCollidable != null) {
                prevcol.x = (int) previousCollidable.getX();
                prevcol.y = (int) previousCollidable.getY();
            }

            if (previousCollidable == null ||
                    pointToLineDistance(centerLine[0], centerLine[1],
                            objloc) <
                            pointToLineDistance(centerLine[0], centerLine[1], prevcol)) {
                approachingCollidables.add(object);
            }
        }

        previousCollidables = collidableObjectsInTriangle;
        return approachingCollidables;
    }

    /**
     * @param a point of line
     * @param b point of line
     * @param p point of object
     * @return returns distance of object from line
     */
    private double pointToLineDistance(Point a, Point b, Point p) {
        double normalLength = Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y));
        return Math.abs((p.x - a.x) * (b.y - a.y) - (p.y - a.y) * (b.x - a.x)) / normalLength;
    }

    @Override
    public void loop() throws MissingPacketException {
        radar.setObjectApproachingCenterLine(getCollidableObjectsApproachingCenterLine(
                centerLineofTraingle(radar.getTrianglePoints()),
                det.getCollidableObjects(radar.getTrianglePoints()[0],
                        radar.getTrianglePoints()[1], radar.getTrianglePoints()[2])));
    }
}

