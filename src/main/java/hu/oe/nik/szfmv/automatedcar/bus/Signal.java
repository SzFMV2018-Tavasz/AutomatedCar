package hu.oe.nik.szfmv.automatedcar.bus;

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

public class Signal {
    // Signal identifier, a component can decide based on this value
    // whether the content of the signal shall be processed or not.
    private SignalEnum id;

    // Signal value
    private Object data;

    /**
     * Creates a signal with an id and some payload.
     *
     * @param id   {@link SignalEnum} the type of the signal
     * @param data the payload of the signal
     */
    public Signal(SignalEnum id, Object data) {
        this.id = id;
        this.data = data;
    }

    // Getter for Signal Id
    public SignalEnum getId() {
        return id;
    }

    // Getter for Signal Value
    public Object getData() {
        return data;
    }
}