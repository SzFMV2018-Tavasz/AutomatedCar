package hu.oe.nik.szfmv.environment.models;


import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PedestrianTest {
    @Test
    public void IsMovable() {
        Pedestrian pedestrian = new Pedestrian(500, 500,"man.png", 1000, 1000);
        assertTrue(Movable.class.isInstance(pedestrian));
    }

    @Test
    public void moveOnCrosswalkOneTimes(){
        Pedestrian pedestrian = new Pedestrian(500, 500,"man.png", 1000, 1000);
        pedestrian.moveOnCrosswalk();
        assertThat(pedestrian.getY(), is(495));
    }

    @Test
    public void moveOnCrosswalkTenTimes(){
        Pedestrian pedestrian = new Pedestrian(500, 500,"man.png", 1000, 1000);
        for (int i = 0; i < 10; i++){
            pedestrian.moveOnCrosswalk();
        }
        assertThat(pedestrian.getY(), is(450));
    }

    @Test
    public void moveOnCrosswalkHundredTimes(){
        Pedestrian pedestrian = new Pedestrian(500, 500,"man.png", 1000, 1000);
        for (int i = 0; i < 100; i++){
            pedestrian.moveOnCrosswalk();
        }
        assertThat(pedestrian.getY(), is(120));
    }
}
