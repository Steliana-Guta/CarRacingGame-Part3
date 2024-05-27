public class PacketLogin extends Packet {
    private String username;
    private float x,y;
    public PacketLogin(byte[] data) {
        super(00);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Float.parseFloat(dataArray[1]);
        this.y = Float.parseFloat(dataArray[2]);
    }
    public PacketLogin(String username, float x, float y) {
        super(00);
        this.username = username;
        this.x = x;
        this.y = y;
    }
    @Override
    public void writeData(Client client) {
        client.sendData(getData());
    } // Sends to  client
    @Override
    public void writeData(Server server) {
        server.sendDataToAllClients(getData());
    } // Sends to server
    @Override
    public byte[] getData() {
        return ("00" + this.username+","+getX()+","+getY()).getBytes();
    } // Return packet status
    public String getUsername() { return username; }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
}
