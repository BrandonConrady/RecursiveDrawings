package recursion;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position rotation(double radians) {
        int x2 = (int) ((x * Math.cos(radians)) - (y * Math.sin(radians)));
        int y2 = (int) ((x * Math.sin(radians)) + (y * Math.cos(radians)));

        return new Position(x2, y2);
    }

    public Position shiftedPosition(int xShift, int yShift) {
        return new Position(this.x + xShift, this.y + yShift);
    }

    public void setPosition(Position p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public String toString() {
        return "(" + x + "," + y + ")";
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
}
