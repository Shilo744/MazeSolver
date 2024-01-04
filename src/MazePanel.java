import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazePanel extends JPanel {
    private Pixel[][] maze;
    private JButton resetButton=new JButton();
    private BufferedImage image;
    private static int imageWidth;
    private static int imageHeight;
    private Pointer pointer;
    private int speed=10000;
    private String path;
    private String png;
    private String jpg;
    private int mazeNumber=5;
    private final int forToMuch=1000000;
    private int toMuch=forToMuch;
    MazePanel() {
        setLayout(null);
        setBackground(Color.black);
        setDoubleBuffered(true);
        resetButton.setBounds(0,0,100,50);
        resetButton.setText("solve");

         path="C:\\Users\\Shilo\\Desktop\\Mazes\\MAZE"+mazeNumber;
         png=".png";
         jpg=".jpg";
        resetButton.addActionListener((e)-> startSolving());
        try {
            setMaze(path+png);
        } catch (IOException e) {
            try {
                setMaze(path+jpg);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        add(resetButton);
    }
    public void startSolving(){
        toMuch=forToMuch;
        pointer=new Pointer(maze);
        repaint();
    }
    public void move(){
            try {
//                for (int i = 0; i < speed; i++)
               while (!pointer.isEnd())
               {
                   toMuch--;
                   if(toMuch==0){
                       startSolving();
                   }
                    if(pointer.isEnd()){
                        break;
                    }
                    if(!pointer.isStuck()){
                    pointer.chooseMove();}
                    else{ pointer.reset();}
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
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                maze[i][j].paint(graphics);
            }
        }
        if(pointer!=null){
        pointer.paint(graphics);
        if(!pointer.isEnd())
        move();}
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
