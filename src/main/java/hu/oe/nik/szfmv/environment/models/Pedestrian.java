package hu.oe.nik.szfmv.environment.models;

public class Pedestrian extends Movable {

    private int moveStatus = 0;
    private int status = 1;

    /**
     * @param x             pedestrian x coordinate
     * @param y             pedestrian y coordinate
     * @param imageFileName pedestrian image file
     */
    public Pedestrian(int x, int y, String imageFileName) {
        super(x, y, imageFileName);
    }

    /**
     * Method of pedestrian move
     */
    public void moveOnCrosswalk() {

        final int movingUnit = 5;
        final int height = 338;
        final int manSize = 72;

        switch (status) {
            case 0: {
                this.move((int) this.getX(), (int) this.getY() + movingUnit, (float) this.getRotation());
                moveStatus -= movingUnit;
                if (moveStatus == 0) {
                    status = 1;
                    this.setY(this.getY() - manSize / 2);
                    setRotation(Math.toRadians(0));
                }
                break;
            }
            case 1: {
                this.move(this.getX(), this.getY() - movingUnit, (float) this.getRotation());
                moveStatus += movingUnit;
                if (moveStatus == height + manSize) {
                    status = 0;
                    this.setY(this.getY() + manSize / 2);
                    setRotation(Math.PI);
                }
                break;
            }
            default:
                break;
        }
    }
}
