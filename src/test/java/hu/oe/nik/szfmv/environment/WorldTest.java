package hu.oe.nik.szfmv.environment;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.environment.models.Pedestrian;
import hu.oe.nik.szfmv.environment.models.Road;
import hu.oe.nik.szfmv.environment.models.RoadSign;
import hu.oe.nik.szfmv.environment.models.Tree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class WorldTest {

    private static final Logger LOGGER = LogManager.getLogger(WorldTest.class);

    @Test
    public void WorldListNull() {
        World testWorld = new World(0, 0);
        Assert.assertNotNull(testWorld.getWorldObjects());
    }

    @Test
    public void WorldHeightNull() {
        World testWorld = new World(0, 0);
        Assert.assertEquals(testWorld.getHeight(), 0);
    }

    @Test
    public void WorldWidthNull() {
        World testWorld = new World(0, 0);
        Assert.assertEquals(testWorld.getWidth(), 0);
    }

    @Test
    public void WorldSetHeight() {
        World testWorld = new World(0, 0);
        testWorld.setHeight(1);
        Assert.assertEquals(testWorld.getHeight(), 1);
    }

    @Test
    public void WorldListSetWidth() {
        World testWorld = new World(0, 0);
        testWorld.setWidth(1);
        Assert.assertEquals(testWorld.getWidth(), 1);
    }

    @Test
    public void WorldListHasXml() {
        World testWorld = new World(0, 0);
        testWorld.build("");
        Assert.assertTrue(!testWorld.getWorldObjects().isEmpty());
    }

    @Test
    public void WorldListNotNull() {
        World testWorld = new World(0, 0);
        testWorld.addObjectToWorld(new Road());
        Assert.assertTrue(!testWorld.getWorldObjects().isEmpty());
    }

    @Test
    public void WorldListProperAdd() {
        World testWorld = new World(0, 0);
        testWorld.addObjectToWorld(new Road());
        Assert.assertTrue(Road.class.isInstance(testWorld.getWorldObjects().get(0)));
    }

    @Test
    public void checkForCollisions() {

        World testWorld = new World(0, 0);
        AutomatedCar car = new AutomatedCar(198, 93, "car_2_red.png");
        Pedestrian pedestrian = new Pedestrian(201, 92, "man.png");
        testWorld.addObjectToWorld(car);
        testWorld.addObjectToWorld(pedestrian);

        testWorld.checkForCollisions(car);
        Assert.assertTrue(testWorld.isColliding(car, pedestrian));
        Assert.assertTrue(testWorld.isGameOver());

    }

    @Test
    public void isGameOver() {
        World testWorld = new World(0, 0);
        AutomatedCar car = new AutomatedCar(199, 90, "car_2_blue.png");
        RoadSign roadSign = new RoadSign(85, 35,"roadsign_speed_40.png");
        LOGGER.debug("isGameOver method::Car: " + car.getShape().getBounds());
        LOGGER.debug("isGameOver method::RoadSign: " + roadSign.getShape().getBounds());

        testWorld.addObjectToWorld(car);
        testWorld.addObjectToWorld(roadSign);

        testWorld.checkForCollisions(car);

        Assert.assertTrue(testWorld.isColliding(car, roadSign));
        Assert.assertFalse(testWorld.isGameOver());

        Pedestrian pedestrian = new Pedestrian(85, 35, "man.png");
        LOGGER.debug("isGameOver method::Pedestrian: " + pedestrian.getShape().getBounds());
        testWorld.addObjectToWorld(pedestrian);

        testWorld.checkForCollisions(car);
        Assert.assertTrue(testWorld.isColliding(car, pedestrian));
        Assert.assertTrue(testWorld.isGameOver());
    }

    @Test
    public void isColliding() {
        World testWorld = new World(0, 0);
        AutomatedCar car = new AutomatedCar(202, 94, "car_2_white.png");
        Tree tree = new Tree(110, 35, "tree.png");

        LOGGER.debug("isColliding method::Car: " + car.getShape().getBounds());
        LOGGER.debug("isColliding method::Tree1: " + tree.getShape().getBounds());
        Assert.assertTrue(testWorld.isColliding(car, tree));

        tree = new Tree(20, 20, "tree.png");
        LOGGER.debug("isColliding method::Tree2: " + tree.getShape().getBounds());
        Assert.assertFalse(testWorld.isColliding(car, tree));
    }
}