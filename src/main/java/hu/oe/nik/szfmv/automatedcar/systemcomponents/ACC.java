package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;
import hu.oe.nik.szfmv.environment.WorldObject;

public class ACC extends SystemComponent {
    private static final int ACCSPEEDMINVALUE = 30;
    private static final int ACCSPEEDMAXVALUE = 160;
    private static final int ACCSPEEDSTEPVALUE = 5;
    private static final double ACC_DISTANCE_STEP = 0.2;
    private static final double ACC_DISTANCE = 0.8;
    private static final double MAX_ACC_DISTANCE = 1.6;

    private boolean isAccOnPressed = false;
    private double accDistanceValue;
    private int accSpeedValue;
    private final InputPacket inputPacket;

    private InputHandler inputHandler;

    /**
     * ACC constructor
     *
     * @param bus is the given functionbus
     */
    public ACC(VirtualFunctionBus bus) {
        super(bus);

        accDistanceValue = 0.8;
        accSpeedValue = 30;

        inputPacket = InputPacket.getInstance();
        inputHandler = InputHandler.getInstance();
    }

    @Override
    public void loop() {
        if (inputHandler.isAccDistancePressed()) {
            rotateDistanceValue();
        }
        if (inputHandler.isAccSpeedIncrementPressedPressed() && !inputHandler.isAccSpeedDecrementPressedPressed()) {
            setAccSpeedValue(+ACCSPEEDSTEPVALUE);
        }
        if (!inputHandler.isAccSpeedIncrementPressedPressed() && inputHandler.isAccSpeedDecrementPressedPressed()) {
            setAccSpeedValue(-ACCSPEEDSTEPVALUE);
        }
        if (isAccOnPressed == true && inputHandler.isBrakePressed()) {
            turnAccOff();
        }

        setAccOn();
        inputPacket.setAccDistanceValue(accDistanceValue);
        inputPacket.setAccSpeedValue(accSpeedValue);

    }

    private void setAccOn() {
        if (inputHandler.isAccOnPressed()) {
            if (!isAccOnPressed) {
                inputPacket.setAccOn(!inputPacket.getACCOn());
                isAccOnPressed = true;
            }
        }

        if (!inputHandler.isAccOnPressed()) {
            isAccOnPressed = false;
        }
    }

    private void turnAccOff() {
        isAccOnPressed = false;
    }

    /**
     * Set the distance value
     */
    private void rotateDistanceValue() {
        accDistanceValue += ACC_DISTANCE_STEP;
        if (accDistanceValue == MAX_ACC_DISTANCE) {
            accDistanceValue = ACC_DISTANCE;
        }
    }

    /**
     * Set the acc speed
     *
     * @param diff is the given diff
     */
    private void setAccSpeedValue(int diff) {
        accSpeedValue += diff;
        if (accSpeedValue < ACCSPEEDMINVALUE) {
            accSpeedValue = ACCSPEEDMINVALUE;
        } else if (accSpeedValue > ACCSPEEDMAXVALUE) {
            accSpeedValue = ACCSPEEDMAXVALUE;
        }
    }

    private WorldObject getTheNPCCar() {
        return null;
    }

    private int getTheDistanceFromTheNPCCar() {
        return 0;
    }

    private void setSpeedWithTempomat() {
        //kell a kocsi aktuális sebessége (ez nem egyenlő az actuallTempomatSpeed-el)
        //kell az actuallTempomatSpeed
        // ha van előttünk npc car akkor a sebességet igazítani ahhoz
        //ha van roadsign (azaz a visszatérési értéke a getTheNearestRoadSignalSpeedValue-nek nem -1) és az kisebb mint
        //az actuallTempomatSpeed akkor aztz beállítani a metódus által visszaadott sebességre.
        // figyelni hogy milyen sebesség volt beállítva korábban, és ha pl tábla, vagy npc miatt ezt visszább kellett venni
        //akkor miután ezek eltűntek előlünk, visszaállítani a beállított tempomat sebességet.
    }
}
