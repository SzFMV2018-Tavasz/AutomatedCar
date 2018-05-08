package hu.oe.nik.szfmv.automatedcar.bus;

import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.car.CarPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.ReadOnlyInputPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.powertrain.PowertrainPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.parkingpilot.ReadOnlyPPCoordinatesPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.powertrain.ReadOnlyPowertrainPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.powertrain.ReadOnlyPowertrainTestPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.roadsigndetection.ReadOnlyRoadSignDetectionPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.reverseradar.ReadOnlyReverseRadarPacket;
import hu.oe.nik.szfmv.automatedcar.bus.packets.ultrasonicsensor.ReadOnlyUltrasonicSensorPacket;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for the Virtual Function Bus. Components are only
 * allowed to collect sensory data exclusively using the VFB. The VFB stores the
 * input and output signals, inputs only have setters, while outputs only have
 * getters respectively.
 */
public class VirtualFunctionBus {

    public PowertrainPacket powertrainPacket;
    public ReadOnlyPowertrainTestPacket powertrainTestPacket;
    public ReadOnlyInputPacket inputPacket;
    public CarPacket carPacket;
    public ReadOnlyRoadSignDetectionPacket roadSignDetectionPacket;
    public ReadOnlyReverseRadarPacket reverseRadarPacket;
    public ReadOnlyUltrasonicSensorPacket ultrasonicSensorPacket;
    public ReadOnlyPPCoordinatesPacket ppCoordinatesPacket;

    private List<SystemComponent> components = new ArrayList<>();

    /**
     * Registers the provided {@link SystemComponent}
     *
     * @param comp a class that implements @{link ISystemComponent}
     */
    public void registerComponent(SystemComponent comp) {
        components.add(comp);
    }

    /**
     * Calls cyclically the registered {@link SystemComponent}s once the virtual function bus has started.
     *
     * @throws MissingPacketException when VirtualFunctionBus packet not initiated
     */
    public void loop() throws MissingPacketException {
        for (SystemComponent comp : components) {
            comp.loop();
        }
    }
}
