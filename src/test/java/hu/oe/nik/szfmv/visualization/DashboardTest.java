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
    private boolean laneKeepingGetterCalled = false;
    private boolean parkingPilotGetterCalled = false;
    private boolean leftTurnSignalGetterCalled = false;
    private boolean rightTurnSignalGetterCalled = false;
    private boolean steeringWheelGetterCalled = false;
    private boolean gearStateGetterCalled = false;

    /**
     * Sets all the boolean values that indicate method calls to false before the tests are run.
     */
    @Before
    public void setUp() {
        gasPedalGetterCalled = false;
        breakPedalGetterCalled = false;
        laneKeepingGetterCalled = false;
        parkingPilotGetterCalled = false;
        distanceLabelGetterCalled = false;
        speedLabelGetterCalled = false;
        gearStateGetterCalled = false;
        steeringWheelGetterCalled = false;
    }

    /**
     * Tests whether all the correct getter methods have been called.
     */
    @Test
    public void allRequiredValuesReceivedOnUpdate() {
        InputPacketStub inputPacket = new InputPacketStub();
        int carX = 0;
        int carY = 0;
        dashboard.updateDisplayedValues(inputPacket, carX, carY);

        assertThat(gasPedalGetterCalled, is(true));
        assertThat(breakPedalGetterCalled, is(true));
        assertThat(parkingPilotGetterCalled, is(true));
        assertThat(laneKeepingGetterCalled, is(true));
        assertThat(distanceLabelGetterCalled, is(true));
        assertThat(speedLabelGetterCalled, is(true));
        assertThat(gearStateGetterCalled, is(true));
        assertThat(leftTurnSignalGetterCalled, is(true));
        assertThat(rightTurnSignalGetterCalled, is(true));
        assertThat(steeringWheelGetterCalled, is(true));
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
            steeringWheelGetterCalled = true;
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
            laneKeepingGetterCalled = true;
            return false;
        }

        @Override
        public boolean getParkingPilotStatus() {
            parkingPilotGetterCalled = true;
            return false;
        }

        @Override
        public GearEnum getGearState() {
            gearStateGetterCalled = true;
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