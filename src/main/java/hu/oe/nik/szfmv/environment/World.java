package hu.oe.nik.szfmv.environment;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PowertrainSystem;
import hu.oe.nik.szfmv.detector.classes.Detector;
import hu.oe.nik.szfmv.environment.interfaces.IWorld;
import hu.oe.nik.szfmv.environment.models.Collidable;
import hu.oe.nik.szfmv.environment.models.Pedestrian;
import hu.oe.nik.szfmv.environment.models.Tree;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class World implements IWorld {
    private static final Logger LOGGER = LogManager.getLogger();

    private int width = 0;
    private int height = 0;
    private List<WorldObject> worldObjects = new ArrayList<>();
    private Detector detector;

    private boolean isGameOver = false;

    /**
     * Creates the virtual world with the given dimension.
     * To populate the world with objects from xml use the build(String xmlLocation) function
     *
     * @param width  the width of the virtual world
     * @param height the height of the virtual world
     */
    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.build("src/main/resources/test.xml");
        detector = new Detector(worldObjects);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    /**
     * @return class responsible for sensor functionality
     */
    public Detector getDetector() {
        return detector;
    }

    /**
     * Add an object to the virtual world.
     *
     * @param o {@link WorldObject} to be added to the virtual world
     */
    public void addObjectToWorld(WorldObject o) {
        worldObjects.add(o);
    }

    @Override
    public void build(String xmlLocation) {
        try {
            worldObjects = XmlToModelConverter.build(xmlLocation);
        } catch (Exception ex) {
            LOGGER.info("Error in World build - " + ex.getMessage());
        }
    }

    /**
     * It is true when fatal collision happened.
     *
     * @return if the game is over.
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Iterate trought all the collidable objects from the world.
     * If collision happens set the speed of the car, or ends the "game".
     *
     * @param car {@link AutomatedCar}  provides the current controleld AutomatedCar.
     */
    public void checkForCollisions(AutomatedCar car) {
        List<WorldObject> collidables = worldObjects.stream().filter(Collidable.class::isInstance).collect(Collectors.toList());
        isGameOver = false;
        for (WorldObject collidable : collidables) {
            if (isColliding(car, collidable)) {
                if (Pedestrian.class.isInstance(collidable) || Tree.class.isInstance(collidable)){
                    isGameOver = true;
                } else {
                    PowertrainSystem.carCollide();
                }
            }
        }
    }

    /**
     * Check if the 2 given {@link WorldObject} collide.
     *
     * @param a a {@link WorldObject} to check
     * @param b a {@link WorldObject} to check
     * @return true if they collide, false if they not.
     */
    public boolean isColliding(WorldObject a, WorldObject b) {
        return a.getShape().intersects(b.getShape().getBounds2D());
    }
}
