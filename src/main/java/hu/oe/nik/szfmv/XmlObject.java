package hu.oe.nik.szfmv;

public class XmlObject {
    final String type;
    Position position;
    double rotation;

    public XmlObject(String type, int x, int y, double r) {
        this.type = type;
        position = new Position(x, y);
        rotation = r;
    }

    class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
