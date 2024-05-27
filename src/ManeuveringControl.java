import java.awt.*;
import java.util.ArrayList;
public class ManeuveringControl {
    private ArrayList<Maneuvering> maneuvers;
    private boolean collision;
    private int width = 40, height = 40;
    public ManeuveringControl () {
        maneuvers = new ArrayList<Maneuvering>(); // Array for drivers
    }
    public void driverCrash() { // To be implemented again once both cars are working
        float playerGreenX = maneuvers.get(0).getX();
        float playerGreenY = maneuvers.get(0).getY();
        Rectangle driverBox = new Rectangle((int) playerGreenX + 5, (int) playerGreenY + 5, width, height); // Only one needed
        // As a detection of the other car touching the driverBox is run bellow
        float playerBlueX = maneuvers.get(1).getX();
        float playerBlueY = maneuvers.get(1).getY();
        // Check if drivers crash/touch
        if (driverBox.intersects(playerBlueX + 5, playerBlueY + 5, width, height)) { collision = true; }
    }
    public void maneuvers() {
        for (int i = 0; i < maneuvers.size(); i++) {
            Maneuvering e = maneuvers.get(i);
            e.initiate();
        }
//        driverCrash(); // Game lost if drivers crash
    }
    public void render(Graphics g) { for(Maneuvering m : maneuvers) { m.render(g); } }
    public void addDriver(Maneuvering m) {
        maneuvers.add(m);
    }
    public ArrayList<Maneuvering> getDrivers() {
        return maneuvers;
    }
    public boolean isCrash() {
        return collision;
    }
    public void removeDriver(String username) {
        int index = 0;
        for (Maneuvering m : maneuvers) {
            if(m instanceof Driver && ((Driver) m).getUsername().equals(username)) { break;}
            index++;
        }this.maneuvers.remove(index);
    }
    private int getDriver(String username) {
        int index = 0;
        for (Maneuvering m : maneuvers) {
            if(m instanceof Driver && ((Driver) m).getUsername().equals(username)) { break;}
            index++;
        }return index;
    }
    public void moveDriver(String username, float x, float y, int currentImageIndex) {
        int index = getDriver(username);
        Driver player = (Driver)this.getDrivers().get(index);
        player.x = x;
        player.y = y;
        player.setCurrentImageIndex(currentImageIndex);
    }
}
