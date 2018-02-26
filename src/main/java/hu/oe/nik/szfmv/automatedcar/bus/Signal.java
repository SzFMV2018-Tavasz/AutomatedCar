package hu.oe.nik.szfmv.automatedcar.bus;

import lombok.Data;

/**
 * This class represent a single signal on the bus. Signals can be handled by
 * implementing the ISystemComponent interface.
 * <p>
 * In a real environment signals are encapsulated by frames (terminology depends
 * on the network) and a frame can contain several signals. This way the
 * throughput of the network is utilized more efficiently.
 * <p>
 * For the simulation environment let's assume that each frame contains a single
 * message, so we do not need to bother extracting signal data from frames.
 */

@Data
public class Signal<T> {
    private SignalEnum id;
    private T data;

    public Signal(SignalEnum id, T data) {
        this.id = id;
        this.data = data;
    }
}