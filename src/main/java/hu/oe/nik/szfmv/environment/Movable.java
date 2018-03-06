package hu.oe.nik.szfmv.environment;


//This class will be the parent of the "car" classes.
public abstract class Movable extends Collidable{


    /**
     * -     * Creates an object of the virtual world on the given coordinates with the given image.
     * -     *
     * -     * @param x             the initial x coordinate of the object
     * -     * @param y             the initial y coordinate of the object
     * -     * @param imageFileName the filename of the image representing the object in the virtual world
     * -
     *
     * @param x
     * @param y
     * @param imageFileName
     */
    public Movable(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    public void move(int newX, int newY, float newRotation)
    {
        this.x = newX;
        this.y = newY;
        this.rotation = newRotation;
    }
}
