import javax.swing.*;
import java.awt.*;
public class Tracking {
    public JFrame f;
    private Canvas c; // Everything drawn on canvas
    private String title; // Game window title
    private int width, height; // Game window size
    public Tracking(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
    }
    private void createDisplay() {
        f = new JFrame(title);
        f.setSize(width,height); // Fame size
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Closing game default
        f.setResizable(false); // Not allowing for game window to be resized
        f.setLocationRelativeTo(null); // Start on centre of screen
        f.setVisible(true); // Set to visible
        c = new Canvas();
        c.setPreferredSize(new Dimension(width, height)); // Set canvas size
        // Keep window at the manually set size
        c.setMinimumSize(new Dimension(width, height)); // Cannot set resizable to false so manually setting min and max
        c.setMaximumSize(new Dimension(width, height)); // Cannot set resizable to false so manually setting min and max
        c.setFocusable(false); // Focused canvas false
        f.add(c); // Add canvas to the frame
        f.pack(); // Set the window size as above
    }
    public Canvas getCanvas() {
        return c;
    } // Get canvas
    public JFrame getFrame() {
        return f;
    } // Get frame
}