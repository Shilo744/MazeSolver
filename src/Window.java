import javax.swing.*;

public class Window extends JFrame {

    Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        MazePanel mazePanel = new MazePanel();
        setSize(mazePanel.getImage().getWidth(), mazePanel.getImage().getHeight());
        setLocationRelativeTo(null);
        add(mazePanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        Window window = new Window();
    }
}
