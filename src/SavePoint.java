import java.awt.*;
import java.util.Stack;

public class SavePoint {
    private Point lastLocation;
    private Stack<Integer> moves;
    private Stack<Point> allTrack=new Stack<>();
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


    public void pushIntoTruck(int x,int y){
        Point point=new Point(x,y);
        allTrack.push(point);
    }
    public Stack<Point> getTrack(){
        return allTrack;
    }
    public void clearTruck(){
        allTrack=new Stack<>();
    }
}
