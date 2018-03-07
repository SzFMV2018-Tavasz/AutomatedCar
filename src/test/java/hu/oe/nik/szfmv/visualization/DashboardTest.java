package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.automatedcar.bus.packets.input.ReadOnlyInputPacket;
import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;
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

        @Override
        public double getSteeringWheelPosition() {
            return 0;
        }

        @Override
        public int getACCTargetSpeed() {
            return 0;
        }

        @Override
        public double getACCTargetDistance() {
            return 0;
        }

        @Override
        public boolean getLaneKeepingStatus() {
            return false;
        }

        @Override
        public boolean getParkingPilotStatus() {
            return false;
        }

        @Override
        public GearEnum getGearState() {
            return null;
        }

        @Override
        public boolean getLeftTurnSignalStatus() {
            return false;
        }

        @Override
        public boolean getRightTurnSignalStatus() {
            return false;
        }
    }
}