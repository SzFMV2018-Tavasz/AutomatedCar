package hu.oe.nik.szfmv.automatedcar.bus;

import hu.oe.nik.szfmv.automatedcar.SystemComponent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VirtualFunctionBusTest {

    private VirtualFunctionBus virtualFunctionBus;
    private boolean receiveSignalFunctionCalled;

    @org.junit.Before
    public void registerComponent() {
        virtualFunctionBus = new VirtualFunctionBus();
        receiveSignalFunctionCalled = false;

        SenderComponentMock senderComponent = new SenderComponentMock(virtualFunctionBus);
        ReceiverComponentMock receiverComponent = new ReceiverComponentMock(virtualFunctionBus);

        virtualFunctionBus.registerComponent(senderComponent);
        virtualFunctionBus.registerComponent(receiverComponent);
    }

    @org.junit.Test
    public void sendSignalTest() {
        virtualFunctionBus.sendSignal(new Signal(SignalEnum.TESTSIGNAL, 42));
        assertTrue(receiveSignalFunctionCalled);
    }

    class SenderComponentMock extends SystemComponent {

        protected SenderComponentMock(VirtualFunctionBus virtualFunctionBus) {
            super(virtualFunctionBus);
        }

        @Override
        public void loop() {
        }

        @Override
        public void receiveSignal(Signal s) {
        }
    }

    class ReceiverComponentMock extends SystemComponent {

        protected ReceiverComponentMock(VirtualFunctionBus virtualFunctionBus) {
            super(virtualFunctionBus);
            subscribeOnSignal(SignalEnum.TESTSIGNAL);
        }

        @Override
        public void loop() {
        }
    }
}
