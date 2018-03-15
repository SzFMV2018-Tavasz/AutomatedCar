package hu.oe.nik.szfmv.automatedcar.bus.packets.sample;

import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;

public interface ReadOnlySamplePacket {
    int getGaspedalPosition();

    int getBrakepedalPosition();

    GearEnum getGearState();
}
