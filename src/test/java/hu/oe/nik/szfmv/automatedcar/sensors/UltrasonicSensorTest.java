package hu.oe.nik.szfmv.automatedcar.sensors;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.Collidable;
import hu.oe.nik.szfmv.environment.models.RoadSign;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UltrasonicSensorTest {

    private static final double THRESHOLD = 0.0001d;
    World w = new World(800, 600);
    AutomatedCar car = new AutomatedCar(0, 0, "");

    @Before
    public void initialize() {
        car.setRotation(Math.toRadians(270));
        car.setX(100);
        car.setY(100);
        w.getWorldObjects().clear();

        RoadSign closer = new RoadSign(120, 60, "roadsign_speed_40.png");
        closer.setWidth(30);
        closer.setHeight(40);
        closer.generateShape();
        w.addObjectToWorld(closer);

        RoadSign other = new RoadSign(150, 100, "roadsign_speed_50.png");
        other.generateShape();
        w.addObjectToWorld(other);
    }

    @Test
    public void testNearestObject() {
        UltrasonicSensor sensor = new UltrasonicSensor(0, 0, 0, car, w);
        assertEquals(sensor.getNearestObject().getImageFileName(), "roadsign_speed_40.png");
    }

    @Test
    public void testNearestObjectDistance() {
        UltrasonicSensor sensor = new UltrasonicSensor(0, 0, 0, car, w);
        assertEquals(sensor.getNearestObjectDistance(), Math.sqrt(2000), THRESHOLD);
    }

    @Test
    public void testNearestObjectDimensions() {
        UltrasonicSensor sensor = new UltrasonicSensor(0, 0, 0, car, w);
        int[] dimensions = sensor.getNearestObjectDimensions();

        assertNotNull(dimensions);
        assertEquals(dimensions[0], 30);
        assertEquals(dimensions[1], 40);
    }
}