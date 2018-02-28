package hu.oe.nik.szfmv.automatedcar.bus.packets.sample;

import lombok.Data;

@Data
public class SamplePacket implements ReadOnlySamplePacket {
    private int gaspedalPosition = 0;
    // TODO implement all of the HMI signals
}
