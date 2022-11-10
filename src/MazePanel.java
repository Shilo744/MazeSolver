import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazePanel extends JPanel {
    private Pixel[][] maze;
    private BufferedImage image;
    private static int imageWidth;
    private static int imageHeight;
    private Pointer pointer;
    private int speed=50;
    MazePanel() {
        setLayout(null);
        setBackground(Color.black);
        setDoubleBuffered(true);

        try {
            setMaze("C:\\Users\\Shilo\\Downloads\\MAZE1.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
            move();

    }
    public void move(){
            try {
                for (int i = 0; i < speed; i++) {
                    if(pointer.isEnd()){
                        break;
                    }
                    if(!pointer.isStuck()){
                    pointer.chooseMove();}
                    else pointer.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        repaint();
    }
    public void setMaze(String location) throws IOException {
        image = ImageIO.read(new File(location));
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        maze = new Pixel[imageHeight][imageWidth];
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                int pixel = image.getRGB(j, i);
                Color pixelColor = new Color(pixel);
                if (pixelColor.getRed() < 200) {
                    maze[i][j] = new Pixel(Color.black, j , i );
                } else {
                    maze[i][j] = new Pixel(Color.white, j  , i);
                }
            }
        }
        pointer = new Pointer(maze);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                maze[i][j].paint(graphics);
            }
        }
        pointer.paint(graphics);
        if(!pointer.isEnd())
        move();
    }

    public BufferedImage getImage() {
        return image;
    }

    public static int getImageWidth() {
        return imageWidth;
    }

    public static int getImageHeight() {
        return imageHeight;
    }
}
