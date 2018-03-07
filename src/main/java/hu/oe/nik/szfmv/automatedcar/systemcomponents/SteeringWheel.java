package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;

public class SteeringWheel extends SystemComponent{

    private static final double minPosition = -100;
    private static final double maxPosition = 100;
    private static final double step = 5;

    private final InputPacket inputPacket;

    private InputHandler inputHandler;

    public SteeringWheel(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        inputPacket = new InputPacket();
        virtualFunctionBus.inputPacket = inputPacket;

        inputHandler = InputHandler.getInstance();
    }

    @Override
    public void loop() {

        if (inputHandler.isSteeringLeftPressed() && inputHandler.isSteeringRightPressed())
            return;

        if (inputHandler.isSteeringLeftPressed()){
            SetSteeringWheelPosition(inputPacket.getSteeringWheelPosition() - step);
            return;
        }

        if (inputHandler.isSteeringRightPressed()){
            SetSteeringWheelPosition(inputPacket.getSteeringWheelPosition() + step);
            return;
        }

        if (inputPacket.getSteeringWheelPosition() == 0)
            return;

        // Ha nincs lenyomva egyik irány sem, és nem középen áll a kormány, a 0 felé közelítjük az állást.
        int sign = inputPacket.getSteeringWheelPosition() > 0 ? -1 : 1;
        SetSteeringWheelPosition(inputPacket.getSteeringWheelPosition() + (sign * step));
    }

    private void SetSteeringWheelPosition(double newPos){

        if (newPos > maxPosition){
            inputPacket.setSteeringWheelPosition(maxPosition);
        }
        else if (newPos < minPosition){
            inputPacket.setSteeringWheelPosition(minPosition);
        }
        else if (Math.abs(newPos) < step) // Ha már egy lépésnyinél kevesebbel térünk el 0-tól, akkor beállítjuk 0-ra, hogy ne ugráljon magától ide oda a kormányállás.
        {
            inputPacket.setSteeringWheelPosition(0);
        }
        else {
            inputPacket.setSteeringWheelPosition(newPos);
        }

        System.out.println(inputPacket.getSteeringWheelPosition());
    }
}
