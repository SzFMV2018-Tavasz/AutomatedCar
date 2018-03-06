package hu.oe.nik.szfmv.environment;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class World {
    private int width = 0;
    private int height = 0;
    String xmlLocation;
    private List<WorldObject> worldObjects = new ArrayList<>();

    /**
     * Creates the virtual world with the given dimension.
     *
     * @param width  the width of the virtual world
     * @param height the height of the virtual world
     */
    public World(int width, int height) {
        this.width = width;
        this.height = height;
        xmlLocation = "src/main/resources/test.xml"; //TODO ide kell majd beírni az XML helyét.
        loadDefaultMapElements(xmlLocation);
    }

    private void loadDefaultMapElements(String xmlLocation){
        try {
            for (WorldObject xmlItem : Visualizing.build(xmlLocation)) {
                this.worldObjects.add(xmlItem);
            }
        }
        catch (Exception e){
            System.out.println("XML ERROR: "+e.getMessage());
        }
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
     * Add an object to the virtual world.
     *
     * @param o {@link WorldObject} to be added to the virtual world
     */
    public void addObjectToWorld(WorldObject o) {
        worldObjects.add(o);
    }
}
