import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
public class Client extends Thread {
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private RaceGround race;
    public Client(RaceGround race, String ipAddress) {
        this.race = race;
        try { this.socket = new DatagramSocket();
            this.ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException e) { e.printStackTrace();
        } catch (UnknownHostException e) { e.printStackTrace();
        }
    }
    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try { socket.receive(packet);
            } catch (IOException e) { e.printStackTrace();
            } this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
        }
    }
    private void parsePacket(byte[] data, InetAddress address, int port) {
        String message = new String(data).trim();
        Packet.PacketStatus status = Packet.searchPacket(message.substring(0, 2));
        Packet packet = null;
        switch(status) {
            default:
            case INVALID: break;
            case LOGIN:
                packet = new PacketLogin(data);
                handleLogin((PacketLogin)packet, address, port);
                break;
            case DISCONNECT:
                packet = new PacketDisconnect(data);
                System.out.println( "["+address.getHostAddress()+":"+port+"] " + ((PacketDisconnect)packet).getUsername()+ " has disconnected...");
                race.maneuvering.removeDriver(((PacketDisconnect) packet).getUsername());
                break;
            case MOVE:
                packet = new PacketMove(data);
                handleMove((PacketMove)packet);
        }
    }
    private void handleMove(PacketMove packet) {
        this.race.maneuvering.moveDriver(packet.getUsername(), packet.getX(), packet.getY(), packet.getCurrentImageIndex());
    }
    private void handleLogin(PacketLogin packet, InetAddress address, int port) {
        System.out.println( "["+address.getHostAddress()+":"+port+"] " + packet.getUsername()+ " has joined the game...");
        Driver playerGreen = new Driver(race, packet.getX(), packet.getY(), packet.getUsername(),Media.playerGreen_move, "arrows",address, port);
        Driver playerBlue = new Driver(race, packet.getX(), packet.getY(), packet.getUsername(),Media.playerBlue_move, "wasd",address, port);
        race.maneuvering.addDriver(playerGreen); // Join game
        race.maneuvering.addDriver(playerBlue); // Join game
    }
    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
        try { socket.send(packet);
        } catch (IOException e) { e.printStackTrace();
        }
    }
}
