package hu.oe.nik.szfmv.visualization;

import hu.oe.nik.szfmv.environment.WorldObject;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Helper classes for drawing world
 */
public abstract class DrawUtils {

    /**
     * Read image from resources and perform require transforms (scaling, rotating)
     * @param object The word object to draw
     * @param scaleRatio The ratio of the scaling
     * @return  Scaled and rotated image to draw to the right place
     */
    public static BufferedImage getTransformedImage(WorldObject object, float scaleRatio) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(ClassLoader.getSystemResource(object.getImageFileName() + ".png").getFile()));
        } catch (IOException e) {

        }

        AffineTransform at = new AffineTransform();

        //at.scale(scaleRatio, scaleRatio);

        at.rotate(object.getRotation(), image.getWidth()/2, image.getHeight()/2);

        /*double offsetX = (image.getWidth()-image.getHeight())/2;
        double offsetY = (image.getWidth()-image.getHeight())/2;

        at.translate(offset,offset);*/


        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image, null);
    }
}
