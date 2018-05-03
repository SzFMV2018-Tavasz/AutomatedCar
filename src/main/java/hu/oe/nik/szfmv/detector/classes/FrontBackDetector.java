package hu.oe.nik.szfmv.detector.classes;

import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.environment.models.Collidable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FrontBackDetector {

    ArrayList<Collidable> previousCollidables;

    /**
     * @param worldobjects Objects from world lol
     */
    public FrontBackDetector(List<WorldObject> worldobjects) {

        previousCollidables = new ArrayList<>();

    }

    /**
     * @param triangle FOW of detector
     * @return returns center line of detector
     */
    private Point[] createCenterLine(Polygon triangle) {
        Point[] p = new Point[2];
        p[0] = new Point(triangle.xpoints[0], triangle.ypoints[0]);
        p[1] = new Point((triangle.xpoints[1] + triangle.xpoints[2]) / 2,
                (triangle.ypoints[1] + triangle.ypoints[2]) / 2);

        return p;
    }

    public ArrayList<Collidable> getPreviousCollidables() {
        return previousCollidables;
    }

    /**
     * @param centerLine                  Centerline of triangle
     * @param collidableObjectsInTriangle objects that are collidable in triangle
     * @return returns collidable objects in the triangle that are closer to the centerline
     */
    List<Collidable> getCollidableObjectsApproachingCenterLine(Point[] centerLine,
                                                               ArrayList<Collidable> collidableObjectsInTriangle) {

        ArrayList<Collidable> approachingCollidables = new ArrayList<Collidable>();


        for (Collidable object : collidableObjectsInTriangle) {

            Collidable previousCollidable = null;
            for (Collidable previousobject : previousCollidables) {
                if (previousobject.getImageFileName().equals(object.getImageFileName())) {
                    previousCollidable = previousobject;
                    break;
                }
            }

            if (previousCollidable == null ||
                    pointToLineDistance(centerLine[0], centerLine[1], (Point) object.getLocation()) <
                            pointToLineDistance(centerLine[0], centerLine[1], (Point) previousCollidable.getLocation())) {
                approachingCollidables.add(object);
            }
        }

        previousCollidables = collidableObjectsInTriangle;
        return approachingCollidables;
    }

    /**
     * @param a point of line
     * @param b point of line
     * @param p point of object
     * @return returns distance of object from line
     */
    private double pointToLineDistance(Point a, Point b, Point p) {
        double normalLength = Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y));
        return Math.abs((p.x - a.x) * (b.y - a.y) - (p.y - a.y) * (b.x - a.x)) / normalLength;
    }
}
