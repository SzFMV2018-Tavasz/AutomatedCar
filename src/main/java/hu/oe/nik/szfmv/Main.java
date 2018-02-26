package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.common.ConfigProvider;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.visualization.Gui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int CYCLE_PERIOD = 200;

    public static void main(String[] args) {

        // log the current debug mode in config
        LOGGER.info(ConfigProvider.provide().getBoolean("general.debug"));

        Gui gui = new Gui();

        // create the world
        World w = new World(800, 600);
        // create an automated car
        AutomatedCar car = new AutomatedCar(20, 20, "car_2_white.png");
        // add car to the world
        w.addObjectToWorld(car);

        while (true) {
            try {
                car.drive();
//                vis.refreshFrame();
                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }

    }
}
