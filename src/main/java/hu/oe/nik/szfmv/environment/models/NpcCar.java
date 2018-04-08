package hu.oe.nik.szfmv.environment.models;

import hu.oe.nik.szfmv.common.SortVertices;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class NpcCar extends Movable {

    private List<WorldObject> roads;
    private int actualRoadTarget = 0;

    /**
     * Creates the NPC car, and initializes the sorted roads so it can do actions on it.
     *
     * @param world The World
     */
    public NpcCar(World world, int x, int y, String imageFileName) {
        super(x, y, imageFileName);
        final int carWidth = 102;
        final int carHeight = 208;
        this.setWidth(carWidth);
        this.setHeight(carHeight);

        roads = new ArrayList<>();
        for (WorldObject object : world.getWorldObjects()) {
            if (!object.getImageFileName().contains("roadsign") && object.getImageFileName().startsWith("road")) { // if it is road we add it to the list
                roads.add(object);
            }
        }

        sortRoads();
    }

    private static int roadComparatorX(WorldObject o1, WorldObject o2) {
        if (o1.getX() == o2.getX()) {
            return 0;
        }
        if (o1.getX() < o2.getX()) {
            return -1;
        }

        return 1;

    }

    private static int roadComparatorY(WorldObject o1, WorldObject o2) {

        if (o1.getY() == o2.getY()) {
            return 0;
        }
        if (o1.getY() < o2.getY()) {
            return -1;
        }

        return 1;

    }

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

    public void Move() {
        // Search for the closest road in the list
        // then make a move unit towards that road
        // calculate the rotation between the road x,y and the car head
        // check if the car is in the road's boundary, if yes, search for the next closest road abd repeat

        // if we are in the actualRoadTarget's image boundary, then we go to the next Road
        double distanceFromActualTarget = Point2D.distance(this.getX(), this.getY(), roads.get(actualRoadTarget).getX(), roads.get(actualRoadTarget).getY());
        if (distanceFromActualTarget <= roads.get(actualRoadTarget).getHeight() ||
                distanceFromActualTarget <= roads.get(actualRoadTarget).getWidth()) {
            if (actualRoadTarget + 1 < roads.size()) {
                actualRoadTarget++;  // then we go to the next road, and target that road, and try to reach it
            } else {
                actualRoadTarget = 0; // we go to the first road, to make a loop
            }
        }

        double roadCenterX = roads.get(actualRoadTarget).getX() + (roads.get(actualRoadTarget).getWidth() / 2);
        double roadCenterY = roads.get(actualRoadTarget).getY() + (roads.get(actualRoadTarget).getHeight() / 2);

        double deltaX = ((roadCenterX - this.getX()) / 100);
        double deltaY = ((roadCenterY - this.getY()) / 100);
        this.setY(this.getY() + deltaY);
        this.setX(this.getX() + deltaX);
    }


}
