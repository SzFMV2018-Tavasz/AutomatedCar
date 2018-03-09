package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public interface IPowertrainSystem {
    /**
     * Get the singals from the VirtualFunctionBus
     */
    void getVirtualFunctionBusSignals();

    void calculateAcceleration();
    void calculateDecceleration();
    void shiftUp();
    void shiftDown();
}
