import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
public class CarFrames {
    private int index; // Index
    private BufferedImage[] frames; //Array of 'frames' of the car images
    public CarFrames(BufferedImage[] frames) {
        this.frames = frames;
        index = 0; //First car image 0 degrees on start line
    }
    public BufferedImage getCurrentFrame(int index) {
        return frames[index];
    } //Used for the Move Sheet image
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(CarFrames.class.getResource(path)); // Returns car images from path
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); // End game if image can't load
        }
        return null;
    }
}