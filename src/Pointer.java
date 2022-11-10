import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class Pointer {
    private int x;
    private int y;
    private Point startPoint;
    private Point lastPoint;
    private Point endPoint;
    private int widthAndHeight = 1;
    private int speed=3;
    private Stack<SavePoint>savePoints=new Stack<>();
    private Stack<Point>junkPoints=new Stack<>();

    private Pixel[][] maze;
    private int up = 1;
    private int right = 2;
    private int down = 3;
    private int left = 4;
    int moveUp = -1;
    int moveDown = 1;
    int moveLeft = -1;
    int moveRight = 1;
    private Stack<Integer> moves = new Stack<>();
    private Color clear = Color.WHITE;
    private boolean end = false;

    public Pointer(Pixel[][] maze) {
        this.maze = maze;
        findStart();
        SavePoint savePoint=new SavePoint(startPoint,moves);
        savePoints.push(savePoint);
        findEnd();
    }

    private void findEnd() {
        boolean stop = false;
        boolean foundPoint = false;
        for (int j = MazePanel.getImageWidth() - 1; j > 0; j--) {
            if (stop) {
                break;
            } else
                for (int i = 0; i < MazePanel.getImageHeight() - 1; i++) {
                    if (maze[i][j].getColor().getRed() < maze[i + 1][j].getColor().getRed()) {
                        if (!foundPoint) {
                            endPoint = new Point(j, i + 1);
                            stop = true;
                            foundPoint = true;
                        }
                    }
                }
        }
    }
    boolean isStuck(){
        for (int i = up; i <= left; i++) {
            if(legalMove(i)){
                return false;
            }
        }
    return true;}
    boolean legalMove(int move) {
        boolean legalMove = true;

        switch (move) {
            case 1 -> {
                legalMove = checkCollusion(up);
            }
            case 2 -> {
                legalMove = checkCollusion(right);
            }
            case 3 -> {
                legalMove = checkCollusion(down);
            }
            case 4 -> {
                legalMove = checkCollusion(left);
                if (x < startPoint.x + 1)
                    legalMove = false;
            }
        }
        return legalMove;
    }

    public void reset() {
        if (!savePoints.isEmpty())
        if(savePoints.peek().getMoves().isEmpty()){
            savePoints.pop();
        }
        SavePoint savePoint=savePoints.peek();
        x= savePoint.getLastLocation().x;
        y= savePoint.getLastLocation().y;
        if(!savePoint.getMoves().isEmpty()){
        makeMove(savePoint.getMoves().pop());}
    }

    private boolean checkCollusion(int move) {
        boolean legalMove = true;
        if (move == up) {
            if(y+moveUp==lastPoint.y){return false;}
            legalMove = checkAllSpaceCollusion(y + moveUp, x, true);
        } else if (move == down) {
            if(y+moveDown==lastPoint.y){return false;}
            legalMove = checkAllSpaceCollusion(y + widthAndHeight, x, true);
        } else if (move == right) {
            if(x+moveRight==lastPoint.x){return false;}
            legalMove = checkAllSpaceCollusion(y, x + widthAndHeight, false);
        } else if (move == left) {
            if(x+moveLeft==lastPoint.x){return false;}
            legalMove = checkAllSpaceCollusion(y, x + moveLeft, false);
        }

        return legalMove;
    }

    private boolean checkAllSpaceCollusion(int mazeY, int mazeX, boolean onX) {

        if (onX) {
            for (int i = mazeX; i < mazeX + widthAndHeight; i++) {
                if (maze[mazeY][i].getColor() != clear) {
                    return false;
                }
            }
        } else {
            for (int i = mazeY; i < mazeY + widthAndHeight; i++) {
                if (maze[i][mazeX].getColor() != clear) {
                    return false;
                }
            }
        }
        return true;
    }

    private void findStart() {
        boolean stop = false;
        boolean foundPoint = false;
        for (int j = 0; j < MazePanel.getImageWidth(); j++) {
            if (stop) {
                break;
            }
            for (int i = 0; i < MazePanel.getImageHeight() - 1; i++) {
                if (maze[i][j].getColor().getRed() < maze[i + 1][j].getColor().getRed()) {
                    if (!foundPoint) {
                        x = j;
                        y = i + 1;
                        lastPoint = new Point(j - 1, i);
                        startPoint = new Point(j, i);
                        stop = true;
                        foundPoint = true;
                    }
                }
                if (stop)
                    try {
                        if (maze[i + 1][j].getColor().getRed() == maze[i + 2][j].getColor().getRed()) {
                            widthAndHeight++;
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public void chooseMove() {
        int choose;
        fillStuck();
        byte random= (byte) (moves.size()*Math.random()-1);
                choose = moves.get(random);
                moves.remove(random);
        makeMove(choose);

    }
    private void fillStuck(){
        if (moves.isEmpty()) {
            for (int i = up; i <= left; i++) {
                moves.push(i);
            }
        }
    }
    private void makeMove(int choose){
        for (int i = 0; i < speed; i++) {
            savePoint();
            switch (choose) {
                case 1 ->moveUp();
                case 2 ->moveRight();
                case 3 -> moveDown();
                case 4 -> moveLeft();
            }
        }
    }
    private void savePoint(){
        Stack<Integer> moves=new Stack<>();
        for (int i = up; i <= left; i++) {
            int move=i;
            if(legalMove(move)){
                moves.push(move);
            }
        }
        int moreThan1ValidOption=1;
        if(moves.size()>moreThan1ValidOption){
            int a=x;
            int b=y;
            SavePoint savePoint=new SavePoint(a,b,moves);
            Point point=new Point(a,b);
            if(!pointExist(savePoint.getLastLocation())){
            savePoints.push(savePoint);
            junkPoints.push(point);}
        }
    }
    private boolean pointExist(Point point){
        for (Point point1:junkPoints) {
            if(point1.getX()==point.getX()&& point1.getY()==point.getY()){
                return true;
            }
        }
    return false;}
    private void updateLastPoint() {
        lastPoint = new Point(x, y);
    }

    public void moveUp() {
        if (lastPoint.getY() != y + moveUp)
            if (legalMove(up)) {
                updateLastPoint();
                y--;
            }
    }

    public void moveDown() {
        if (lastPoint.getY() != y + moveDown)
            if (legalMove(down)) {
                updateLastPoint();
                y++;
            }
    }

    public void moveLeft() {
        if (lastPoint.getX() != x + moveLeft)
            if (legalMove(left)) {
                updateLastPoint();
                x--;
            }
    }
    public void moveRight() {
        if (lastPoint.getX() != x + moveRight)
            if (legalMove(right)) {
                updateLastPoint();
                x++;
            }
    }

    public void paint(Graphics graphics) {
        graphics.setColor(Color.green);
        graphics.fillRect(x, y, widthAndHeight, widthAndHeight);
        if(isEnd()){
            int maxColor=255;
            int green=0;
            int blue=0;
            int red=0;
            int chance=20;
            int upTo=((int)maxColor /chance)*chance;
            Color toGreen=new Color(red,green,blue);
                for (int i = 0; i < savePoints.size(); i++) {
                    Point point=savePoints.get(i).getLastLocation();
                    graphics.setColor(toGreen);
                    if(green<upTo){
                        green+=chance;
                    }else if(blue<upTo){
                        blue+=chance;
                    }else if(red<upTo){
                        red+=chance;
                    }
                    try {
                        toGreen=new Color(red,green,blue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    graphics.fillRect(point.x,point.y,widthAndHeight,widthAndHeight);
                }
                }
    }

    public boolean isEnd() {
        if (endPoint.getX() <= x) {
            end = true;
        }
        return end;
    }
}
