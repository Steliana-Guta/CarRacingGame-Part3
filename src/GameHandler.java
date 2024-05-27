import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
public class GameHandler implements WindowListener{
    private final RaceGround race;
    public GameHandler(RaceGround race) {
        this.race = race;
        race.track.f.addWindowListener(this);
    }
    @Override
    public void windowActivated(WindowEvent event) {}
    @Override
    public void windowClosed(WindowEvent event) {}
    @Override
    public void windowClosing(WindowEvent event) {
        PacketDisconnect packetGreen = new PacketDisconnect(this.race.playerGreen.getUsername());
//        PacketDisconnect packetBlue = new PacketDisconnect(this.race.playerBlue.getUsername());
        packetGreen.writeData(this.race.socketClient); // Remove player from server when close window
//        packetBlue.writeData(this.race.socketClient); // Remove player from server when close window
    }
    @Override
    public void windowDeactivated(WindowEvent event) {}
    @Override
    public void windowDeiconified(WindowEvent event) {}
    @Override
    public void windowIconified(WindowEvent event) {}
    @Override
    public void windowOpened(WindowEvent event) {}
}
