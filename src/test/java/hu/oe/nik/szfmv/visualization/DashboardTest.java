package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.automatedcar.bus.packets.input.ReadOnlyInputPacket;
import hu.oe.nik.szfmv.automatedcar.input.enums.GearEnum;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DashboardTest {

    private Dashboard dashboard = new Dashboard();
    private boolean gasPedalGetterCalled = false;
    private boolean breakPedalGetterCalled = false;

    private boolean distanceLabelGetterCalled = false;
    private boolean speedLabelGetterCalled = false;

    private boolean leftTurnSignalGetterCalled = false;
    private boolean rightTurnSignalGetterCalled = false;

    /**
     * Sets all the boolean values that indicate method calls to false before the tests are run.
     */
    @Before
    public void setUp() {
        gasPedalGetterCalled = false;
        breakPedalGetterCalled = false;

        distanceLabelGetterCalled = false;
        speedLabelGetterCalled = false;
    }

    /**
     * Tests whether all the correct getter methods have been called.
     */
    @Test
    public void allRequiredValuesReceivedOnUpdate() {
        InputPacketStub inputPacket = new InputPacketStub();
        dashboard.updateDisplayedValues(inputPacket);

        assertThat(gasPedalGetterCalled, is(true));
        assertThat(breakPedalGetterCalled, is(true));

        assertThat(distanceLabelGetterCalled, is(true));
        assertThat(speedLabelGetterCalled, is(true));

        assertThat(leftTurnSignalGetterCalled, is(true));
        assertThat(rightTurnSignalGetterCalled, is(true));
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
            distanceLabelGetterCalled = true;
            return 0;
        }

        @Override
        public double getACCTargetDistance() {
            speedLabelGetterCalled = true;
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
            leftTurnSignalGetterCalled = true;
            return false;
        }

        @Override
        public boolean getRightTurnSignalStatus() {
            rightTurnSignalGetterCalled = true;
            return false;
        }
    }
}