import java.awt.*;
import java.util.Stack;

public class SavePoint {
    private Point lastLocation;
    private Stack<Integer> moves;

    public SavePoint(int x,int y, Stack moves) {
        this.lastLocation = new Point(x,y);
        this.moves = moves;
    }
    public SavePoint(Point point, Stack moves) {
        this.lastLocation = new Point(point.x,point.y);
        this.moves = moves;
    }

    public Point getLastLocation() {
        return lastLocation;
    }

    public Stack<Integer> getMoves() {
        return moves;
    }
}
