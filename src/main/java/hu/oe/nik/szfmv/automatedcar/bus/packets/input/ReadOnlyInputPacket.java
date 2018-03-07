package hu.oe.nik.szfmv.automatedcar.bus.packets.input;

public interface ReadOnlyInputPacket {
    /**
     * Gets the gas pedal's current position
     * @return the gas pedal's current position
     */
    int getGasPedalPosition();

    /**
     * Gets the break pedal's current position
     * @return the break pedal's current position
     */
    int getBreakPedalPosition();

}