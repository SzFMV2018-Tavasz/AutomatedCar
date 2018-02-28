package hu.oe.nik.szfmv.environment;

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

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public float getRotation() {
        return this.rotation;
    }

    public String getImageFileName() {
        return this.imageFileName;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof WorldObject)) return false;
        final WorldObject other = (WorldObject) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getX() != other.getX()) return false;
        if (this.getY() != other.getY()) return false;
        if (this.getWidth() != other.getWidth()) return false;
        if (this.getHeight() != other.getHeight()) return false;
        if (Float.compare(this.getRotation(), other.getRotation()) != 0) return false;
        final Object this$imageFileName = this.getImageFileName();
        final Object other$imageFileName = other.getImageFileName();
        if (this$imageFileName == null ? other$imageFileName != null : !this$imageFileName.equals(other$imageFileName))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getX();
        result = result * PRIME + this.getY();
        result = result * PRIME + this.getWidth();
        result = result * PRIME + this.getHeight();
        result = result * PRIME + Float.floatToIntBits(this.getRotation());
        final Object $imageFileName = this.getImageFileName();
        result = result * PRIME + ($imageFileName == null ? 43 : $imageFileName.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof WorldObject;
    }

    public String toString() {
        return "WorldObject(x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", rotation=" + this.getRotation() + ", imageFileName=" + this.getImageFileName() + ")";
    }
}