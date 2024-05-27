import java.awt.*;
import java.awt.image.BufferedImage;
public abstract class GrassCollision extends Maneuvering {
    public static final float DEFAULT_SPEED = 0f; // Speed starting at 0 = stopped
    public static final int DEFAULT_PLAYER_WIDTH = 50;
    public static final int DEFAULT_PLAYER_HEIGHT = 50;
    protected int laps;
    protected float speed;
    protected float xMove, yMove;
    boolean collision, passCheckpoint;
    private Rectangle grass;
    public GrassCollision(RaceGround race, float x, float y, int width, int height, BufferedImage[] car) {
        super(race, x, y, width, height);
        speed = DEFAULT_SPEED;
        laps = 0;
        collision = false;
        //Speed and direction for the car at start
        xMove = 0;
        yMove = 0;
        grass = new Rectangle(150, 200, 550, 300);
    }
    public void laps(){
        Rectangle grassBox = new Rectangle(((int)(x + 5)), ((int)(y + 5)), boundary.width, boundary.height); // Diver boundary
        Rectangle endLine = new Rectangle( 700, 350, 800, 1 ); // End line/Finish line
        Rectangle checkpoint = new Rectangle( 50, 350, 100, 1 ); // Ensure drivers race the lap and cross the checkpoint before end line
        // Check if the checkpoint line has been passed
        // Issue is this work if laps are done backwards as well as if checkpoint is passed then car turns around for the finish line
        if (grassBox.intersects(checkpoint)) {passCheckpoint = true;}
        // If the end line and checkpoint has been passed return 'Winner!'
        if (grassBox.intersects(endLine) && passCheckpoint) {
            System.out.println("Winner!");
            laps++;
            passCheckpoint = false;
        }
    }
    public void moveX() { // Grass touching detection
        if (xMove > 0) { // Moving right
            if (!grass.contains((int) (x + boundary.width), y + 5) && !grass.contains((x + boundary.width), boundary.height) && x - boundary.width < 710  ) {
                x += xMove;
                collision = false;
            } else {
                collision = true;
                Media.sound.get("grass").play();
                setSpeed(0); // When cars crash speed is set to 0
            }
        }else if (xMove < 0) { // Moving left
            // When grass is touched the car stops allowing for driver to reverse if needed
            if (!grass.contains(x + 5, y + 5) && !grass.contains(x + 5, boundary.height) && x > 50 ) {
                x += xMove;
                collision = false;
            }else {
                collision = true;
                Media.sound.get("grass").play();
                setSpeed(0); // When grass is touched the car stops allowing for driver to reverse if needed
            }
        }
    }
    public void moveY() {
        if (yMove < 0) { // Moving up
            if (!grass.contains(x + 5, y + 5) && !grass.contains(boundary.width, y + 5 ) && y > 100  ) {
                y += yMove;
                collision = false;
            }else {
                collision = true;
                Media.sound.get("grass").play();
                setSpeed(0); // When grass is touched the car stops allowing for driver to reverse if needed
            }
        } else if (yMove > 0) { // Moving down
            if (!grass.contains(x + 5, y + boundary.height + 5) && !grass.contains(boundary.width, y + boundary.height + 5) && y < 550 ) {
                y += yMove;
                collision = false;
            }else {
                collision = true;
                Media.sound.get("grass").play();
                setSpeed(0); // When grass is touched the car stops allowing for driver to reverse if needed
            }
        }
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public void direction(int currentImageIndex) {
        // Driver moves based on current angle
        if (currentImageIndex == 0 ) {
            yMove = yMove - 2 * speed;
        } else if (currentImageIndex == 1 ) {
            xMove = xMove + 1 * speed;
            yMove = yMove - 2 * speed;
        } else if (currentImageIndex == 2 ) {
            xMove = xMove + 2 * speed;
            yMove = yMove - 2 * speed;
        } else if (currentImageIndex == 3 ) {
            xMove = xMove + 2 * speed;
            yMove = yMove - 1 * speed;
        } else if (currentImageIndex == 4 ) {
            xMove = xMove + 2 * speed;
        }else if (currentImageIndex == 5 ) {
            xMove = xMove + 2 * speed;
            yMove = yMove + 1 * speed;
        }else if (currentImageIndex == 6 ) {
            xMove = xMove + 2 * speed;
            yMove = yMove + 1 * speed;
        }else if (currentImageIndex == 7 ) {
            xMove = xMove + 1 * speed;
            yMove = yMove + 2 * speed;
        }else if (currentImageIndex == 8 ) {
            yMove = yMove + 2 * speed;
        }else if (currentImageIndex == 9 ) {
            xMove = xMove - 1 * speed;
            yMove = yMove + 2 * speed;
        }else if (currentImageIndex == 10 ) {
            xMove = xMove - 2 * speed;
            yMove = yMove + 2 * speed;
        }else if (currentImageIndex == 11 ) {
            xMove = xMove - 2 * speed;
            yMove = yMove + 1 * speed;
        }else if (currentImageIndex == 12 ) {
            xMove = xMove - 2 * speed;
        }else if (currentImageIndex == 13 ) {
            xMove = xMove - 2 * speed;
            yMove = yMove - 1 * speed;
        }else if (currentImageIndex == 14 ) {
            xMove = xMove - 2 * speed;
            yMove = yMove - 2 * speed;
        }else if (currentImageIndex == 15 ) {
            xMove = xMove - 1 * speed;
            yMove = yMove - 2 * speed;
        }
    }
}
