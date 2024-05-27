public class PacketDisconnect extends Packet {
    private String username;
    public PacketDisconnect(byte[] data) { super(01);
        this.username = readData(data);
    }
    public PacketDisconnect(String username) { super(01);
        this.username = username;
    }
    @Override
    public void writeData(Client client) {
        client.sendData(getData());
    } // Sends to client
    @Override
    public void writeData(Server server) {
        server.sendDataToAllClients(getData());
    } // Sends to server
    @Override
    public byte[] getData() {
        return ("01" + this.username).getBytes();
    } // Return packet status
    public String getUsername() {
        return username;
    } // Return username
}