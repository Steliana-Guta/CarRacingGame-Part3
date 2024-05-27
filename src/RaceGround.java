import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferStrategy;
public class RaceGround implements Runnable{
    public Tracking track;
    public int width, height; // Variables for the game window
    public String title; // Game window title
    private boolean running = false; // When players crash: running = false
    private Thread thread;
    private BufferStrategy bs;
    private Graphics g;
    // States
    private State gameState;
    private State menuState;
    private KeyboardControl keyControl; // Key input
    public ManeuveringControl maneuvering;
    private MouseControl mouseControl; // Mouse input
    private Handler handler; // Handler
    public GameHandler gameHandler;
    public SpeedControl playerGreen;
    public SpeedControl playerBlue; // Not currently in use, to be re-integrated
    public Client socketClient;
    public Server socketServer;
    public KeyboardControl getKeyControl() {
        return keyControl;
    } // Keys control
    public MouseControl getMouseControl() {
        return mouseControl;
    } // Mouse control
    public RaceGround(String title, int width, int height) {
        this.width = width; // Game window width
        this.height = height; // Game window height
        this.title = title; // Game window title
        keyControl = new KeyboardControl();
        mouseControl = new MouseControl();
    }
    private void initiate() {
        track = new Tracking(title, width, height);
        track.getFrame().addKeyListener(keyControl);
        // Need both for good focus
        track.getFrame().addMouseListener(mouseControl);
        track.getCanvas().addMouseListener(mouseControl);
        Media.initiate(); // Loads all car pictures
        gameHandler = new GameHandler(this);
        maneuvering = new ManeuveringControl();
        playerGreen = new Driver(this, 750, 350, JOptionPane.showInputDialog(this, "Username"), Media.playerGreen_move, "arrows" ,null, -1); // Original player
//        playerBlue = new PlayerMP(this, 750, 350, JOptionPane.showInputDialog(this, "Username"), Media.playerBlue_move, "wasd" ,null, -1); // Original player
        maneuvering.addDriver(playerGreen);
//        maneuvering.addDriver(playerBlue);
        PacketLogin loginPacketGreen = new PacketLogin(playerGreen.getUsername(), playerGreen.x, playerGreen.y); // New packet, getting name and location
//        PacketLogin loginPacketBlue = new PacketLogin(playerBlue.getUsername(), playerBlue.x, playerBlue.y);
        if (socketServer != null) {
            socketServer.addConnection((Driver)playerGreen, loginPacketGreen); // Connect driver to server
//            socketServer.addConnection((PlayerMP)playerBlue, loginPacketBlue);
        }
        loginPacketGreen.writeData(socketClient);
//        loginPacketBlue.writeData(socketClient);
    }
    private void keys() { // Read keyboard
        keyControl.keys();
        maneuvering.maneuvers();
    }
    private void render() {
        bs = track.getCanvas().getBufferStrategy(); // Buffers to build/draw to game
        if(bs == null) {
            track.getCanvas().createBufferStrategy(3); // Triple buffer
            return;
        }
        g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g.create();
        // Clear to hide flashing track update
        g.clearRect(0, 0, width, height);
        g.fillRect(0, 0, width, height); // Track
        Color c1 = new Color(0,153,0); // Dark Green color for the grass
        g.setColor( c1 );
        g.fillRect( 150, 200, 550, 300 ); // Inner grass
        Color c2 = Color.black;
        g.setColor( c2 );
        g.drawRect(50, 100, 750, 500);  // Outer edge of road
        g.drawRect(150, 200, 550, 300); // Inner edge of road
        Color c3 = Color.yellow;
        g.setColor( c3 );
        g.drawRect( 100, 150, 650, 400 ); // Road center line
        Color c4 = Color.white;
        g.setColor( c4 );
        g.fillRect( 700, 350, 800, 5 ); // Start line
        g.setColor(Color.red);
        g.drawLine(50, 350, 150, 350 ); // Checkpoint
        Area outer = new Area(new Rectangle(0, 0, 850, 650 )); // Outer grass area ends
        Rectangle inner = new Rectangle(50, 100, 750, 500); // Outer grass area start
        outer.subtract(new Area(inner)); // Subtract anything in the middle to fill the outer
        g2d.setColor(new Color(0,153,0));  // Dark Green color for the grass
        g2d.fill(outer); // Fill outer grass in dark green
        maneuvering.render(g);
        bs.show(); // Make visible
        g.dispose(); // Clear
        g2d.dispose(); // Clear
    }
    public State getGameState() {
        return gameState;
    }
    public void run() {
        running = true;
        initiate();
        // Manually setting speed settings, needed for part 3 so all machines use the same settings
        int fps = 60; // Frames per sec
        double timePerTick = 1000000000 / fps; // Measuring time
        double delta = 0;
        long now;
        long lastTime = System.nanoTime(); // System cloc
        // Loop for the game
        while(running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            lastTime = now;
            if(delta >= 1) {
                keys();
                render();
                delta--;
            }
        } stop();
    }
    public synchronized void start() { // Start game and connect to server if user opted YES
        running = true;
        int reply = JOptionPane.showConfirmDialog(null, "Run server?", "Server", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            socketServer = new Server(this);
            socketServer.start();
            System.out.println("Server");
        }
        new Thread(this).start();
        socketClient = new Client(this, "127.0.0.1");
        socketClient.start();
    }
    public synchronized void stop() { /// Stop thread
        running = false;
        try { thread.join(); // Thread stop
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
