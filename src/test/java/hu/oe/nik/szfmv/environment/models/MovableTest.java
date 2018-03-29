package hu.oe.nik.szfmv.environment.models;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.bus.exception.MissingPacketException;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;

import static org.junit.Assert.*;

public class MovableTest extends SystemComponent{



    /**
     * @param virtualFunctionBus VirtualFunctuonBus parameter
     */
    protected MovableTest(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public void loop() throws MissingPacketException {

    }
}