package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public interface IPowertrainSystem {
    /**
     * Get the singals from the VirtualFunctionBus
     */
    void getVirtualFunctionBusSignals();

    void calculateSpeed();
    void calculateDecceleration();
    void shiftUp();
    void shiftDown();
}
