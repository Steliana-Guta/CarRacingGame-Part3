import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
public class Server extends Thread {
    private DatagramSocket socket;
    private RaceGround race;
    private ArrayList<Driver> connectedDriver = new ArrayList<Driver>();
    public Server(RaceGround race) {
        this.race = race;
        try { this.socket = new DatagramSocket(1331);
        } catch (SocketException e) { e.printStackTrace(); }
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
        Packet.PacketStatus type = Packet.searchPacket(message.substring(0, 2));
        Packet packet = null;
        switch(type) {
            default:
            case INVALID: break;
            case LOGIN:
                packet = new PacketLogin(data);
                System.out.println( "["+address.getHostAddress()+":"+port+"] " + ((PacketLogin)packet).getUsername()+ " has connected...");
                Driver playerGreen = new Driver(race, 750, 350, ((PacketLogin) packet).getUsername() ,Media.playerGreen_move, address, port);
//                PlayerMP playerBlue = new PlayerMP(race, 750, 350, ((PacketLogin) packet).getUsername() ,Media.playerBlue_move, address, port);
                this.addConnection(playerGreen, (PacketLogin)packet);
//                this.addConnection(playerBlue, (PacketLogin)packet);
                break;
            case DISCONNECT:
                packet = new PacketDisconnect(data);
                System.out.println( "["+address.getHostAddress()+":"+port+"] " + ((PacketDisconnect)packet).getUsername()+ " has left the game...");
                this.removeConnection((PacketDisconnect)packet);
                break;
            case MOVE:
                packet = new PacketMove(data);
                //System.out.println(((Packet02Move)packet).getUsername()+" has moved to " +((Packet02Move)packet).getX()+","+ ((Packet02Move)packet).getY());
                this.handleMove((PacketMove)packet);
        }
    }
    public void removeConnection(PacketDisconnect packet) {
        this.connectedDriver.remove(getDriverID(packet.getUsername()));
        packet.writeData(this);
    }
    public Driver getDriver(String username) {
        for(Driver player: this.connectedDriver) {
            if(player.getUsername().equals(username)){
                return player;
            }
        } return null;
    }
    public int getDriverID(String username) {
        int id = 0;
        for(Driver player: this.connectedDriver) {
            if(player.getUsername().equals(username)){
                break;
            } id++;
        } return id;
    }
    public void addConnection(Driver player, PacketLogin packet) {
        boolean alreadyConnected = false;

        for (Driver p : this.connectedDriver) {
            if (player.getUsername().equalsIgnoreCase(p.getUsername())) {
                if (p.ipAddress == null) {
                    p.ipAddress = player.ipAddress;
                }
                if (p.port == -1) {
                    p.port = player.port;
                }
                alreadyConnected = true;
            } else {
                sendData(packet.getData(), p.ipAddress, p.port); // Tells current player about new player
                packet = new PacketLogin(p.getUsername(), p.x, p.y);
                sendData(packet.getData(), player.ipAddress, player.port); // Tells new player about current player
            }
        } if(!alreadyConnected) {
            this.connectedDriver.add(player);
            //packet.writeData(this);
        }
    }
    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try { this.socket.send(packet);
        } catch (IOException e) { e.printStackTrace(); }
    }
    public void sendDataToAllClients(byte[] data) {
        for (Driver p : connectedDriver) { sendData(data, p.ipAddress, p.port); }
    }
    private void handleMove(PacketMove packet) {
        if (getDriver(packet.getUsername()) != null) {
            int index = getDriverID(packet.getUsername());
            Driver player = this.connectedDriver.get(index);
            player.x = packet.getX();
            player.y = packet.getY();
            player.setCurrentImageIndex(packet.getCurrentImageIndex()); // Player direction information
            packet.writeData(this);
        }
    }
}

