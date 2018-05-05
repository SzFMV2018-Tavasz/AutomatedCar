package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.LKA.LKAPointsPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.LKA.ReadOnlyLKAPointsPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection.ReadOnlyRoadSignDetectionPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection.RoadSignDetectionPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;
import hu.oe.nik.szfmv.detector.classes.Detector;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.Road;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LaneKeeping extends SystemComponent {

    private List<Road> roads;

    private Detector detector;

    private List<WorldObject> worldObjects;

    private ReadOnlyRoadSignDetectionPacket roadSignDetector;

    private ReadOnlyLKAPointsPacket pointsPacket;

    private InputHandler inputHandler;

    private InputPacket inputPacket;

    private boolean wasPressed;

    private boolean laneKeepingOn;

    /**
     * Lanekeeping Constructor
     *
     * @param virtualFunctionBus is the given functionbus
     */
    public LaneKeeping(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        inputPacket = InputPacket.getInstance();
        virtualFunctionBus.inputPacket = inputPacket;
        inputHandler = InputHandler.getInstance();

        wasPressed = false;
        laneKeepingOn = false;

        detector = Detector.getDetector();
        worldObjects = detector.getWorldObjects();
        roads = new ArrayList<>();
        roadSignDetector = RoadSignDetectionPacket.getInstance();
        pointsPacket = LKAPointsPacket.getInstance();
    }

    @Override
    public void loop() {
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
            for (WorldObject worldObject : seenByCamera) {
                if(worldObject instanceof Road) {
                    if (worldObject.getShape() == null) {
                        worldObject.generateShape();
                    }
                    if(worldObject.getShape().contains(pointsPacket.getLeftPoint())) {
                        //TODO turnleft
                    } else if(worldObject.getShape().contains(pointsPacket.getRightPoint())) {
                        //TODO turnright
                    }
                }
            }
        }
    }
}
