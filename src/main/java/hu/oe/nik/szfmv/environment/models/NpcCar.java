package hu.oe.nik.szfmv.environment.models;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.common.SortVertices;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;



public class NpcCar extends Movable {

    private List<WorldObject> roads;
    private int actualRoadTarget = 0;
    private static final Logger LOGGER = LogManager.getLogger(NpcCar.class);
    private static final double CAR_SPEED = 10;
    private static final double MAX_TURN_DEG_FRAME = 10;

    /**
     * Creates the NPC car, and initializes the sorted roads so it can do actions on it.
     *
     * @param world         World
     * @param x             the Car's x coordinate
     * @param y             the Car's y coordinate
     * @param imageFileName the Car's image filename
     */
    public NpcCar(World world, int x, int y, String imageFileName) {
        super(x, y, imageFileName);
        final int carWidth = 102;
        final int carHeight = 208;
        this.setWidth(carWidth);
        this.setHeight(carHeight);

        // filter out the world objects which are roads
        roads = new ArrayList<>();
        for (WorldObject object : world.getWorldObjects()) {
            if (!object.getImageFileName().contains("roadsign") && object.getImageFileName().startsWith("road")) { // if it is road we add it to the list
                roads.add(object);
            }
        }

        sortRoads();

        WorldObject firstRoad = roads.get(0);
        double deltaY = firstRoad.getY() - this.getY();
        double deltaX = firstRoad.getX() - this.getX();
        double rot = Math.toRadians(270) - Math.atan2(deltaY, deltaX);

        this.setRotation(rot);
    }

    /**
     * Sorts the road objects
     * After the sort it is traversable in counter-clockwise motion
     */
    private void sortRoads() {
        ArrayList<Point2D> points2d = new ArrayList<>();

        for (WorldObject road : roads) {
            points2d.add(new Point2D.Double(road.getX(), road.getY()));
        }

        List<Point2D> pointsSortVertices = new ArrayList<>();
        SortVertices sortvertices = new SortVertices();
        pointsSortVertices = sortvertices.SortClockWise(points2d);

        for (int j = 0; j < pointsSortVertices.size(); j++) {
            for (int i = 0; i < roads.size(); i++) {
                // we match the coordinates and the world objects.
                if (roads.get(i).getX() == pointsSortVertices.get(j).getX() &&
                        roads.get(i).getY() == pointsSortVertices.get(j).getY()) {
                    // if it is not at the same position then swap
                    if (i != j) {
                        // we have to swap the position
                        WorldObject tmp;
                        tmp = roads.get(j);
                        roads.set(j, roads.get(i));
                        roads.set(i, tmp);
                    }
                }
            }
        }

        // set the closest road block to go first
        int minDistance = 0;
        for (WorldObject road : roads) {
            if (Point2D.distance(road.getX(), road.getY(), this.getX(), this.getY())
                    < Point2D.distance(roads.get(minDistance).getX(), roads.get(minDistance).getY(), this.getX(), this.getY())) {
                minDistance = roads.indexOf(road);
            }
        }

        actualRoadTarget = minDistance;
    }

    /**
     * Sets the next roadTarget, and moves the car towards it
     */
    public void move() {
        // Search for the closest road in the list
        // then make a move unit towards that road
        // calculate the rotation between the road x,y and the car head
        // check if the car is in the road's boundary, if yes, search for the next closest road abd repeat

        // if we are in the actualRoadTarget's image boundary, then we go to the next Road
        int errorMargin = 40; // this will be substracted from the boundaries


        double actualTargetX = roads.get(actualRoadTarget).getX();
        double actualTargetY = roads.get(actualRoadTarget).getY();

        double distanceFromActualTarget = Point2D.distance(this.getX(), this.getY(), actualTargetX, actualTargetY);

        if (distanceFromActualTarget <= roads.get(actualRoadTarget).getHeight() - errorMargin ||
                distanceFromActualTarget <= roads.get(actualRoadTarget).getWidth() - errorMargin) {
            if (actualRoadTarget + 1 < roads.size()) {
                actualRoadTarget++;  // then we go to the next road, and target that road, and try to reach it

            } else {
                actualRoadTarget = 0; // we go to the first road, to make a loop
            }
        }

        double roadCenterX = roads.get(actualRoadTarget).getX() + (roads.get(actualRoadTarget).getWidth() / 2);
        double roadCenterY = roads.get(actualRoadTarget).getY() + (roads.get(actualRoadTarget).getHeight() / 2);

        double deltaY = roadCenterY - this.getY();
        double deltaX = roadCenterX - this.getX();

        double targetRotation = (Math.toRadians(270) - Math.atan2(deltaY, deltaX)) % (2*Math.PI);
        double angleDifference = (this.getRotation() - targetRotation) % (2*Math.PI);

        if (Math.abs(angleDifference) > Math.toRadians(MAX_TURN_DEG_FRAME)) {
            if (angleDifference < 0) {
                targetRotation = this.getRotation() + Math.toRadians(MAX_TURN_DEG_FRAME);
            } else if (angleDifference > 0) {
                targetRotation = this.getRotation() - Math.toRadians(MAX_TURN_DEG_FRAME);
            }
        }

        // TODO: don't allow instant rotation

        deltaX = Math.sin(targetRotation) * -CAR_SPEED;
        deltaY = Math.cos(targetRotation) * -CAR_SPEED;

        this.setX(this.getX() + deltaX);
        this.setY(this.getY() + deltaY);

        this.setRotation(Math.toRadians(270) - Math.atan2(deltaY, deltaX));
    }

}
