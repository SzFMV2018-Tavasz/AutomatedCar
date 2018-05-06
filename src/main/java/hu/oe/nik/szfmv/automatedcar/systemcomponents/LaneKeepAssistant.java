package hu.oe.nik.szfmv.automatedcar.systemcomponents;


import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.LKA.LKAPointsPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.LKA.ReadOnlyLKAPointsPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.car.CarPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection.ReadOnlyRoadSignDetectionPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection.RoadSignDetectionPacket;
import hu.oe.nik.szfmv.detector.classes.Detector;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.Road;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringWheel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class LaneKeepAssistant extends SystemComponent {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int leftXOffset = -(300 + 51);
    private static final int rightXOffset = 120 + 51;
    private static final int leftYOffset = -(150 + 104);
    private static final int rightYOffset = -(150 + 104);
    private CarPacket carPacket;
    private LKAPointsPacket pointsPackage = LKAPointsPacket.getInstance();
    private ReadOnlyRoadSignDetectionPacket roadSignDetector;

    private List<Road> roads;
    private Detector detector;

    private InputHandler inputHandler;
    private InputPacket inputPacket;
    private boolean wasPressed;
    private boolean laneKeepingOn;

    Point left;
    Point right;

    /**
     * @param virtualFunctionBus VirtualFunctuonBus parameter
     */
    public LaneKeepAssistant(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        this.carPacket = virtualFunctionBus.carPacket;

        inputPacket = InputPacket.getInstance();
        virtualFunctionBus.inputPacket = inputPacket;
        inputHandler = InputHandler.getInstance();

        wasPressed = false;
        laneKeepingOn = false;

        detector = Detector.getDetector();
        roads = new ArrayList<>();
        roadSignDetector = RoadSignDetectionPacket.getInstance();
    }

    @Override
    public void loop() throws MissingPacketException {
        left = new Point((int) carPacket.getX() + leftXOffset, (int) carPacket.getY() + leftYOffset);
        right = new Point((int) carPacket.getX() + rightXOffset, (int) carPacket.getY() + rightYOffset);
        double theta = -carPacket.getRotation();

        Point leftRotated = (Point) left.clone();
        Point rightRotated = (Point) right.clone();
        AffineTransform.getRotateInstance(theta, carPacket.getX(), carPacket.getY()).transform(left, leftRotated);
        AffineTransform.getRotateInstance(theta, carPacket.getX(), carPacket.getY()).transform(right, rightRotated);

        pointsPackage.setLeftPoint(leftRotated);
        pointsPackage.setRightPoint(rightRotated);

        if (!wasPressed && inputHandler.isLaneKeepingPressed()) {
            wasPressed = true;
            laneKeepingOn = !laneKeepingOn;
            inputPacket.setLaneKeepingStatus(laneKeepingOn);
        } else if (wasPressed && !inputHandler.isLaneKeepingPressed()) {
            wasPressed = false;
        }

        if(laneKeepingOn) {
            Point[] points = roadSignDetector.getTrianglePoints();
            List<WorldObject> seenByCamera = detector.getWorldObjects(points[0], points[1], points[2]);
            if(shouldStop(seenByCamera)) {
                //TODO stopLKA
            } else {
                for (WorldObject worldObject : seenByCamera) {
                    if (worldObject instanceof Road) {
                        if (worldObject.getShape().contains(leftRotated)) {
                            //TODO turnleft
                        } else if (worldObject.getShape().contains(rightRotated)) {
                            //TODO turnright
                        }
                    }
                }
            }
        }
    }

    /**
     * @param worldObjects contains the list of WorldObjects seen by camera
     */
    private boolean shouldStop(List<WorldObject> worldObjects) {
        //Gets closest Road object in sight; null if there isn't any
        Point startPoint = roadSignDetector.getTrianglePoints()[0];
        Road closestRoad = null;
        double minDistance = Integer.MAX_VALUE;
        for(WorldObject worldObject : worldObjects) {
            if (worldObject instanceof Road) {
                if (worldObject.getShape() == null) {
                    worldObject.generateShape();
                }
                Rectangle objectRectangle = worldObject.getShape().getBounds();
                Point objectCenter = new Point(objectRectangle.x + objectRectangle.width / 2,
                        objectRectangle.y + objectRectangle.height / 2);
                double currentDistance = startPoint.distance(objectCenter);
                if(currentDistance < minDistance) {
                    closestRoad = (Road) worldObject;
                    minDistance = currentDistance;
                }
            }
        }

        //Returns true if lanekeeping should stop: when there is no road or when there is a 90 degrees turn; return false otherwise
        if(closestRoad == null) {
            return true;
        } else {
            String imageName = closestRoad.getImageFileName();
            if(imageName.contains("road_2lane_6") ||
                    imageName.contains("road_2lane_45") ||
                    imageName.contains("road_2lane_straight")) {
                return false;
            } else {
                return true;
            }
        }
    }

}
