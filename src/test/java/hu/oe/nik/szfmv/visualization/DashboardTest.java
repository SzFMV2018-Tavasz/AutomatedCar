package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.automatedcar.bus.packets.input.ReadOnlyInputPacket;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DashboardTest {

    Dashboard dashboard = new Dashboard();
    boolean gasPedalGetterCalled = false;
    boolean breakPedalGetterCalled = false;

    @Before
    public void setup() {
        gasPedalGetterCalled = false;
        breakPedalGetterCalled = false;
    }

    @Test
    public void allRequiredValuesReceivedOnUpdate() {
        InputPacketStub inputPacket = new InputPacketStub();
        dashboard.updateDisplayedValues(inputPacket);

        assertThat(gasPedalGetterCalled, is(true));
        assertThat(breakPedalGetterCalled, is(true));
    }

    class InputPacketStub implements ReadOnlyInputPacket {
        @Override
        public int getGasPedalPosition() {
            breakPedalGetterCalled = true;
            return 0;
        }

        @Override
        public int getBreakPedalPosition() {
            gasPedalGetterCalled = true;
            return 0;
        }
    }
}