package hu.oe.nik.szfmv.environment;

import lombok.Data;

@Data
public class WorldObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected float rotation = 0f;
    protected String imageFileName;

    /**
     * Creates an object of the virtual world on the given coordinates with the given image.
     *
     * @param x             the initial x coordinate of the object
     * @param y             the initial y coordinate of the object
     * @param imageFileName the filename of the image representing the object in the virtual world
     */
    public WorldObject(int x, int y, String imageFileName) {
        this.x = x;
        this.y = y;
        this.imageFileName = imageFileName;
    }
}