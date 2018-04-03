package hu.oe.nik.szfmv.automatedcar.input;

import hu.oe.nik.szfmv.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SteeringWheel;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SteeringWheelTest {

    private VirtualFunctionBus virtualFunctionBus;
    private SteeringWheel steeringWheel;

    @Before
    public void registerComponent() {
        virtualFunctionBus = new VirtualFunctionBus();

        steeringWheel = new SteeringWheel(virtualFunctionBus);
    }

    @Test
    public void calculateNewSteeringWheelPositionOverMaxTest(){

        double newPos = 150;

        double calculatedPos = steeringWheel.calculateNewSteeringWheelPosition(newPos);
        assertThat(calculatedPos, is((double)100));

    }
}