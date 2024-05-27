import java.awt.image.BufferedImage;
import java.net.InetAddress;
public class Driver extends SpeedControl {
    public InetAddress ipAddress;
    public int port;
    public Driver(RaceGround race, float x, float y, String username, BufferedImage[] car , String tempControls, InetAddress ipAddress, int port) {
        super(race, x, y, car, tempControls, username);
        this.ipAddress = ipAddress;
        this.port = port;
    }
    //For local player
    public Driver(RaceGround race, float x, float y, String username, BufferedImage[] car, InetAddress ipAddress, int port) {
        super(race, x, y, car, null, username);
        this.ipAddress = ipAddress;
        this.port = port;
    }
    @Override
    public void initiate() {super.initiate(); } // Maintain same connection with player1
}
