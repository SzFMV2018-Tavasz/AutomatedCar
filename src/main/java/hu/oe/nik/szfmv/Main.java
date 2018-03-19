package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.common.ConfigProvider;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.models.Pedestrian;
import hu.oe.nik.szfmv.visualization.Gui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int CYCLE_PERIOD = 40;

    /**
     * Main entrypoint of the software
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        final int worldWidth = 800;
        final int worldHeight = 600;
        final int carX = 20;
        final int carY = 20;
        final int carWidth = 108;
        final int carHeight = 240;
        final int pedestrianX = 1350;
        final int pedestrianY = 500;
        final int crosswalkX = 1495;
        final int crosswalkY = 486;

        // log the current debug mode in config
        LOGGER.info(ConfigProvider.provide().getBoolean("general.debug"));

        // create the world
        World w = new World(worldWidth, worldHeight);
        // create an automated car
        AutomatedCar car = new AutomatedCar(carX, carY, "car_2_white.png");
        car.setWidth(carWidth);
        car.setHeight(carHeight);
        // add car to the world
        w.addObjectToWorld(car);

        Pedestrian pedestrian = new Pedestrian(pedestrianX, pedestrianY, "man.png", crosswalkX, crosswalkY);
        w.addObjectToWorld(pedestrian);

        // create gui
        Gui gui = new Gui();

        // draw world to course display
        gui.getCourseDisplay().drawWorld(w);

        while (true) {
            try {
                car.drive();
                pedestrian.moveOnCrosswalk();

                gui.getCourseDisplay().drawWorld(w);
                gui.getDashboard().updateDisplayedValues(car.getInputValues(), car.getX(), car.getY());
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
}
