public class PacketMove extends Packet {
    private String username;
    private float x,y;
    private int currentImageIndex;
    public PacketMove(byte[] data) {
        super(02);
        String[] dataArray = readData(data).split(",");
        this.username = dataArray[0];
        this.x = Float.parseFloat(dataArray[1]);
        this.y = Float.parseFloat(dataArray[2]);
        this.currentImageIndex =  Integer.parseInt(dataArray[3]);
    }
    public PacketMove(String username, float x, float y, int currentImageIndex) {
        super(02);
        this.username = username;
        this.x = x;
        this.y = y;
        this.currentImageIndex = currentImageIndex;
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
        return ("02" + this.username + ","+this.x +","+this.y+","+this.currentImageIndex).getBytes(); // Get
    }
    public String getUsername() {
        return username;
    } // Get username
    public float getX() {
        return this.x;
    } // Return x position
    public float getY() {
        return this.y;
    } // Return y position
    public int getCurrentImageIndex() {
        return currentImageIndex;
    } // Return car image
}