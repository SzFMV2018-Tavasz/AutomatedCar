package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.parkingpilot.PPCoordinatesPacket;
import hu.oe.nik.szfmv.automatedcar.sensors.UltrasonicSensor;
import hu.oe.nik.szfmv.common.Utils;
import hu.oe.nik.szfmv.environment.models.Collidable;

import java.util.List;

public class ParkingPilotFunction extends SystemComponent {

    private List<UltrasonicSensor> ultrasonicSensors;
    private PPCoordinatesPacket ppCoordinatesPacket;
    private double startY;
    private double endY;
    private double axisX;
    private Collidable gameObject;

    private boolean spaceCalcStarted;
    private boolean spaceCalcEnded;

    private final int carHalfWidth = 51;
    private final int carHalfHeight = 104;

    /**
     * ParkingPilotFunction constructor
     *
     * @param virtualFunctionBus is the given functionbus
     */
    public ParkingPilotFunction(VirtualFunctionBus virtualFunctionBus, List<UltrasonicSensor> ultrasonicSensors) {
        super(virtualFunctionBus);
        this.ultrasonicSensors = ultrasonicSensors;
        ppCoordinatesPacket = new PPCoordinatesPacket();
        virtualFunctionBus.ppCoordinatesPacket = ppCoordinatesPacket;
        gameObject = null;
    }

    @Override
    public void loop() throws MissingPacketException {
        if (virtualFunctionBus.inputPacket.getParkingPilotStatus() && !spaceCalcEnded){
            if (virtualFunctionBus.inputPacket.getRightTurnSignalStatus()){
                searchRightSide();
            }
            if (virtualFunctionBus.inputPacket.getLeftTurnSignalStatus()){
                searchLeftSide();
            }
        }
        if (spaceCalcEnded){
            sendPacket();
        }
        if(!virtualFunctionBus.inputPacket.getParkingPilotStatus()){
            spaceCalcStarted = false;
            spaceCalcEnded = false;
            gameObject = null;
        }

    }

    private void searchLeftSide(){
        if (!spaceCalcStarted && ultrasonicSensors.get(6).getNearestObject() == null){
            axisX = virtualFunctionBus.carPacket.getX() - carHalfWidth - Utils.convertMeterToPixel(3);
            startY = virtualFunctionBus.carPacket.getY() + carHalfHeight;
        }
        if (spaceCalcStarted && ultrasonicSensors.get(6).getNearestObject() != null){
            gameObject = ultrasonicSensors.get(6).getNearestObject();
            endY = gameObject.getY() + (gameObject.getHeight() / 2);
        }
    }

    private void searchRightSide(){
        if (!spaceCalcStarted && ultrasonicSensors.get(4).getNearestObject() == null){
            axisX = virtualFunctionBus.carPacket.getX() + carHalfWidth;
            startY = virtualFunctionBus.carPacket.getY() + carHalfHeight;
        }
        if (spaceCalcStarted && ultrasonicSensors.get(4).getNearestObject() != null){
            gameObject = ultrasonicSensors.get(4).getNearestObject();
            endY = gameObject.getY() + (gameObject.getHeight() / 2);
        }
    }

    private void sendPacket(){
        double frontX = axisX - virtualFunctionBus.carPacket.getX();
        double frontY = endY - virtualFunctionBus.carPacket.getY();
        double backX = axisX - virtualFunctionBus.carPacket.getX();
        double backY = startY - virtualFunctionBus.carPacket.getY();

        ppCoordinatesPacket.setFrontX(frontX);
        ppCoordinatesPacket.setFrontY(frontY);
        ppCoordinatesPacket.setBackY(backY);
        ppCoordinatesPacket.setBackX(backX);
        ppCoordinatesPacket.setPlaceIsAvailable(true);

    }
}
