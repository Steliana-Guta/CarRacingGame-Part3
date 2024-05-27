public abstract class Packet {
    public static enum PacketStatus {
        INVALID(-1), LOGIN(00), DISCONNECT(01), MOVE(02); // Packet status
        private int packetID;  // ID
        private PacketStatus(int packetID) {
            this.packetID = packetID;
        }
        public int getId() {
            return packetID;
        } // Get ID
    }
    public byte packetID;
    public Packet(int packetID) {
        this.packetID = (byte) packetID;
    } // get the packet relevant to ID
    public abstract void writeData(Client client); // Sends to specific client
    public abstract void writeData(Server server); //Sends to server
    public String readData (byte[] data) { // Read
        String message = new String(data).trim();
        return message.substring(2);
    }
    public abstract byte[] getData(); // Get
    public static PacketStatus searchPacket(String packetId) { try { return searchPacket(Integer.parseInt(packetId)); //Ensures correct code is given back
        } catch (NumberFormatException e) { return PacketStatus.INVALID;}
    }
    public static PacketStatus searchPacket(int id) { // Search packet status
        for (PacketStatus p : PacketStatus.values()) { if (p.getId() == id) { return p;}
        }return PacketStatus.INVALID;
    }
}
