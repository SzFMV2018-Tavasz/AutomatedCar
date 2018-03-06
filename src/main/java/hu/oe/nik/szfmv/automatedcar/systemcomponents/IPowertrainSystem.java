package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public interface IPowertrainSystem {
    void getVirtualFunctionBusSignals();
    void calculateAcceleration();
    void calculateDecceleration();
    void shiftUp();
    void shiftDown();
}
