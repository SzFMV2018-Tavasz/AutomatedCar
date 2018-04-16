package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.bus.packets.input.InputPacket;
import hu.oe.nik.szfmv.automatedcar.input.InputHandler;
import hu.oe.nik.szfmv.environment.WorldObject;

public class Tempomat extends SystemComponent {
    private final int DEFAULTTEMPOMATSPEED = 30;
    private int actuallTempomatSpeed;
    private boolean isTempomatOn = false;
    private final InputPacket inputPacket;
    private InputHandler inputHandler;

    public Tempomat(VirtualFunctionBus bus) {

        super(bus);
        inputPacket = InputPacket.getInstance();
        inputHandler = InputHandler.getInstance();
    }

    public int getActuallTempomatSpeed() {
        return actuallTempomatSpeed;
    }

    public void setActuallTempomatSpeed(int actuallTempomatSpeed) {
        this.actuallTempomatSpeed = actuallTempomatSpeed;
    }

    public boolean isTempomatOn() {
        return isTempomatOn;
    }

    public void turnTempomatOn() {
        if (true) { // ha van beállítva a dashboardon tempomatsebesség akkor azt kell állítani, különben default
            actuallTempomatSpeed = 0; // itt kell valahogy a dashboradról kiszedni az értéket
        } else {
            actuallTempomatSpeed = DEFAULTTEMPOMATSPEED;
        }
        isTempomatOn = true;
    }

    public void turnTempomatOff() {
        isTempomatOn = false;
    }

    private int getTheNearestRoadSignalSpeedValue() {
        if (true) {//ha van a közelben roadsign
            return 0;//
        }

        return -1; //ha nincs a közelben roadsign akkor vissza -1
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

    @Override
    public void loop() throws MissingPacketException {
        if (isTempomatOn()) {
            if (inputHandler.isLaneKeepingPressed()) { // ez nem a laneKeeping lesz, csak nincs még gomb a tempomathoz
                if (this.isTempomatOn) {
                    turnTempomatOff();
                } else {
                    turnTempomatOn();
                }
            } else if (inputHandler.isBrakePressed()) {
                turnTempomatOff();
            }
        }
    }
}
