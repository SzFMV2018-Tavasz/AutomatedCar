package hu.oe.nik.szfmv.environment.models;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.Map;

public class Road extends Crossable {
    private static HashMap<String, Polygon> roadPolyMap;

    static {
        roadPolyMap = new HashMap<String, Polygon>();
        roadPolyMap.put(
                "road_2lane_90left.png",
                new Polygon(
                        //525x525
                        new int[]{0, 143, 227, 307, 358, 408, 461, 502, 525, 175, 162, 140, 97, 48, 0},
                        new int[]{0, 21, 51, 96, 138, 197, 272, 364, 525, 525, 461, 422, 380, 356, 350},
                        15
                ));
        roadPolyMap.put(
                "road_2lane_90right.png",
                //525x525
                new Polygon(
                        new int[]{525, 382, 297, 212, 177, 117, 64, 23, 0, 350, 363, 385, 428, 477, 525},
                        new int[]{0, 21, 51, 96, 138, 197, 272, 364, 525, 525, 461, 422, 380, 356, 350},
                        15
                ));
        roadPolyMap.put(
                "road_2lane_45left.png",
                //366x366
                new Polygon(
                        new int[]{0, 248, 325, 358, 382, 398, 400, 50, 48, 43, 36, 27, 17},
                        new int[]{248, 0, 97, 162, 230, 310, 370, 370, 342, 321, 300, 285, 267},
                        13
                ));

        roadPolyMap.put(
                //400x370
                "road_2lane_45right.png",
                new Polygon(
                        new int[]{400, 152, 75, 42, 18, 2, 0, 350, 352, 357, 364, 373, 382},
                        new int[]{248, 0, 97, 162, 230, 310, 370, 370, 342, 321, 300, 285, 267},
                        13
                ));
        roadPolyMap.put(
                "road_2lane_6left.png",
                new Polygon(
                        new int[]{0, 347, 355, 361, 364, 366, 17, 13, 8},
                        new int[]{37, 0, 71, 153, 226, 366, 366, 211, 129},
                        9
                ));

        roadPolyMap.put(
                //366x366
                "road_2lane_6right.png",
                new Polygon(
                        new int[]{366, 19, 11, 5, 2, 0, 349, 253, 358},
                        new int[]{37, 0, 71, 153, 226, 366, 366, 211, 129},
                        9
                ));
        roadPolyMap.put(
                "road_2lane_rotary.png",
                new Polygon(
                        new int[]{0, 0, 350, 450, 525, 525, 875, 875, 950, 1050, 1399, 1399, 1050, 950, 875, 875, 525, 525, 450, 350},
                        new int[]{875, 525, 525, 450, 350, 0, 0, 350, 450, 525, 525, 875, 875, 950, 1050, 1399, 1399, 1050, 950, 875},
                        20
                ));
//874*1399
        roadPolyMap.put(
                "road_2lane_tjunctionleft.png",
                //1399x965
                new Polygon(
                        new int[]{0, 0, 350, 450, 525, 525, 875, 875, 525, 525, 450, 350},
                        new int[]{875, 525, 525, 450, 350, 0, 0, 1399, 1399, 1050, 950, 875},
                        12
                ));


        roadPolyMap.put(
                "road_2lane_tjunctionright.png",
                //1399x965
                new Polygon(
                        new int[]{875, 875, 525, 425, 350, 350, 0, 0, 350, 350, 425, 525},
                        new int[]{875, 525, 525, 450, 350, 0, 0, 1399, 1399, 1050, 950, 875},
                        12
                ));
    }

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public Road(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }


    /**
     * Creates an object with default parameter values.
     */
    public Road() {
        super(0, 0, null);
    }


    @Override
    public void generateShape() {
        AffineTransform at = new AffineTransform();
        //at.translate(this.getX(), this.getY());
        at.rotate(-this.getRotation(), this.getX() , this.getY() );
        this.shape = at.createTransformedShape(roadPolyMap.get(this.imageFileName));

        //ha a map nem tartalmazza:
        if (this.shape == null) {
            super.generateShape();
        }
    }

}
