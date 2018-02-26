package hu.oe.nik.szfmv.environment;

public class WorldObject {
    // the position of the object
    protected int x;
    protected int y;
    // the dimensions of the object
    protected int width;
    protected int height;
    // the rotation of the object
    protected float rotation = 0f;
    // the file name of the image used to visualise the object
    // it should be in the resources folder, so only the filename is required
    protected String imageFileName;

    public WorldObject(int x, int y, String imageFileName) {
        this.x = x;
        this.y = y;
        this.imageFileName = imageFileName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
