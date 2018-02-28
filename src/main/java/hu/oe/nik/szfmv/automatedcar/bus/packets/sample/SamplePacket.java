package hu.oe.nik.szfmv.automatedcar.bus.packets.sample;

public class SamplePacket implements ReadOnlySamplePacket {
    private int gaspedalPosition = 0;

    public SamplePacket() {
    }

    public int getGaspedalPosition() {
        return this.gaspedalPosition;
    }

    public void setGaspedalPosition(int gaspedalPosition) {
        this.gaspedalPosition = gaspedalPosition;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof SamplePacket)) return false;
        final SamplePacket other = (SamplePacket) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getGaspedalPosition() != other.getGaspedalPosition()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getGaspedalPosition();
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof SamplePacket;
    }

    public String toString() {
        return "SamplePacket(gaspedalPosition=" + this.getGaspedalPosition() + ")";
    }
    // TODO implement all of the HMI signals
}
