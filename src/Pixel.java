import java.awt.*;

public class Pixel {
    private Color color;
    private int x;
    private int y;
    private int widthAndHeight = 1;

    public Pixel(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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

    public void paint(Graphics graphics) {
        graphics.setColor(color);
        graphics.fillRect(x, y, widthAndHeight, widthAndHeight);
    }
}
