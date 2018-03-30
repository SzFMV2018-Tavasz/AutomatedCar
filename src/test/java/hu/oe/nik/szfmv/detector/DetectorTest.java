package hu.oe.nik.szfmv.detector;

import hu.oe.nik.szfmv.detector.classes.Detector;
import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.models.Collidable;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class DetectorTest {
    World w = new World(800, 600);

    @Test
    public void ItHasAllWorldObjects() {
        Detector dec = new Detector(w.getWorldObjects());
        Assert.assertNotEquals(dec.getWorldObjects(new Point(0, 0), new Point(5000, 0), new Point(0, 5000)).size(), 0);
    }

    @Test
    public void OnlyCollidableObjects() {
        Detector dec = new Detector(w.getWorldObjects());
        List<Collidable> obj = dec.getCollidableObjects(new Point(0, 0), new Point(5000, 0), new Point(0, 5000));
        assertTrue(Collidable.class.isInstance(obj.get(0)));

    }
}
