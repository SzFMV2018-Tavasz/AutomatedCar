package hu.oe.nik.szfmv.automatedcar.systemcomponents;


import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.LKA.LKAPointsPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.car.CarPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringWheel;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class LaneKeepAssistant extends SystemComponent {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int leftXOffset = -(300 + 51);
    private static final int rightXOffset = 120 + 51;
    private static final int leftYOffset = -(150 + 104);
    private static final int rightYOffset = -(150 + 104);
    private CarPacket carPacket;
    private LKAPointsPacket pointsPackage = LKAPointsPacket.getInstance();

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
    }


}
