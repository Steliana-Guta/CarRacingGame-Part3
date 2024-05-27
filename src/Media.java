import java.awt.image.BufferedImage;
import java.util.HashMap;
public class Media {
    private BufferedImage sheet; // The move sheet image
    private Media(BufferedImage sheet) {
        this.sheet = sheet;
    }
    private static final int width = 50, height = 50, rows = 4, cols = 4;
    public static BufferedImage[] playerGreen_move; // Array containing images for each angle of the cars
    public static BufferedImage[] playerBlue_move; // Array containing images for each angle of the cars
    public static BufferedImage fire; // Fire/Crash image
    public static HashMap<String, SoundPlayer> sound; // Sounds only run once
    public static HashMap<String, MusicPlayer> music; // Music is continuously running
    public static void initiate() {
        Media sheetGreen = new Media(CarFrames.loadImage("/GreenCar/MoveSheet.png")); // Green car move sheet image path
        Media sheetBlue = new Media(CarFrames.loadImage("/BlueCar/MoveSheet.png")); // Blue car move sheet image path
        playerGreen_move = new BufferedImage[16]; // Array for the 16 cropped images fo the car
        playerBlue_move = new BufferedImage[16]; // Array for the 16 cropped images of the car
        // Loop for te Move Sheet image cropping the 16 images for the cars
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                playerGreen_move[(i*cols) + j] = sheetGreen.cut(j*width, i*height, width, height);
                playerBlue_move[(i*cols) + j] = sheetBlue.cut(j*width, i*height, width, height);
            }
        }
        fire = CarFrames.loadImage("/fire/fire.png"); // Fire picture path
        // Sound effects
        sound = new HashMap<String, SoundPlayer>();
        sound.put("grass", new SoundPlayer("./media/sounds/grass.wav")); // Grass sound path
        sound.put("crash", new SoundPlayer("./media/sounds/crash.wav")); // Crash sound path
        sound.put("speed", new SoundPlayer("./media/sounds/speed.wav")); // Speed sound path
        sound.put("start", new SoundPlayer("./media/sounds/start.wav")); // Start sound path
        // Game music
        music = new HashMap<String, MusicPlayer>();
        music.put("menu", new MusicPlayer("menu")); // Menu music path
    }
    public BufferedImage cut(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }
}