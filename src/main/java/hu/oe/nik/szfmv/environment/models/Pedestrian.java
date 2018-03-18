package hu.oe.nik.szfmv.environment.models;

public class Pedestrian extends Movable {

    private int crosswalkX;
    private int crosswalkY;
    private final int width = 199;
    private final int height = 338;
    private int moveStatus = 0;
    private int moveDirection = 5;

    /**
     *
     * @param x pedestrian x coordinate
     * @param y pedestrian y coordinate
     * @param imageFileName pedestrian image file
     * @param crosswalkX crosswalk x coordinate
     * @param crosswalkY crosswalk y coordinate
     */
    public Pedestrian(int x, int y, String imageFileName, int crosswalkX, int crosswalkY) {
        super(x, y, imageFileName);
        this.crosswalkX = crosswalkX;
        this.crosswalkY = crosswalkY;
    }

    /**
     * Method of pedestrian move
     */
    public void moveOnCrosswalk() {
        this.move(this.getX(), this.getY() - moveDirection, (float) this.getRotation());
        final int movingUnit = 5;

        moveStatus += movingUnit;
        if (moveStatus == height + 102) {
            moveStatus = 0;
            // this.rotation = (float) Math.PI - this.rotation;
            moveDirection = -moveDirection;
        }

    }
}
