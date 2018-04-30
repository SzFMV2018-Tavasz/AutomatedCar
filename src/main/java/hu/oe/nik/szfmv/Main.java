package hu.oe.nik.szfmv;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.common.ConfigProvider;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.models.NpcCar;
import hu.oe.nik.szfmv.environment.models.Pedestrian;
import hu.oe.nik.szfmv.visualization.Gui;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int CYCLE_PERIOD = 40;
    private static final int PARKING_NPC_X = 535;
    private static final int PARKING_NPC1_Y = 1235;
    private static final int PARKING_NPC2_Y = 1835;
    private static final String RED_CAR = "car_2_red.png";

    /**
     * Main entrypoint of the software
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        final int worldWidth = 800;
        final int worldHeight = 600;
        final int carX = 200;
        final int carY = 200;
        final int pedestrianX = 1550;
        final int pedestrianY = 500;

        // log the current debug mode in config
        LOGGER.info(ConfigProvider.provide().getBoolean("general.debug"));

        // create the world
        World w = new World(worldWidth, worldHeight);
        // create an automated car
        AutomatedCar car = new AutomatedCar(carX, carY, "car_2_white.png");
        // add car to the world
        w.addObjectToWorld(car);

        NpcCar npcCar = new NpcCar(w, 500, 500, RED_CAR);
        w.addObjectToWorld(npcCar);

        createParkingCarNPCs(w);

        Pedestrian pedestrian = new Pedestrian(pedestrianX, pedestrianY, "man.png");
        w.addObjectToWorld(pedestrian);

        // create gui
        Gui gui = new Gui();

        // draw world to course display
        gui.getCourseDisplay().drawWorld(w, car.getCarValues(), car.getInputValues(), car.getRoadSign());

        while (!w.isGameOver()) {
            try {
                car.drive();
                pedestrian.moveOnCrosswalk();
                npcCar.move();
                gui.getCourseDisplay().drawWorld(w, car.getCarValues(), car.getInputValues(), car.getRoadSign());
                gui.getDashboard().updateDisplayedValues(
                        car.getInputValues(),
                        car.getPowertrainValues(),
                        car.getRoadSign(),
                        (int) Math.round(car.getX()), (int) Math.round(car.getY()));

                w.checkForCollisions(car);

                Thread.sleep(CYCLE_PERIOD);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    /**
     * Creates the parking car NPCs.
     * @param w the world the cars are put into
     */
    private static void createParkingCarNPCs(World w) {
        NpcCar parkingCar1 = new NpcCar(w, PARKING_NPC_X, PARKING_NPC1_Y, RED_CAR);
        parkingCar1.setRotation(0);
        w.addObjectToWorld(parkingCar1);

        NpcCar parkingCar2 = new NpcCar(w, PARKING_NPC_X, PARKING_NPC2_Y, RED_CAR);
        parkingCar2.setRotation(0);
        w.addObjectToWorld(parkingCar2);
    }
}
