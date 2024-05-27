import javax.swing.*;
import java.awt.*;
public class Menu extends State { // Not currently in use, to be re-integrated
    private Rectangle playButton = new Rectangle(handler.getGame().width / 2 - 50, 200, 100, 50); // Play button in Menu
    private Rectangle helpButton = new Rectangle(handler.getGame().width / 2 - 50, 350, 100, 50); // Help button in Menu
    private Rectangle quitButton = new Rectangle(handler.getGame().width / 2 - 50, 500, 100, 50); // Quit button in Menu
    private int x = 0; // Variable for loop of menu animation
    public Menu(Handler handler){
        super(handler);
        this.handler = handler;
        Media.music.get("menu").run(); // Start menu music
    }
    @Override
    public void readUserAction() {
        if(handler.getMouseManager().play) {
            Media.music.get("menu").stop(); // Once play button clicked, menu music stops
            Media.sound.get("start").play(); // Start sound starts
            State.setState(handler.getGame().getGameState());
        } else if (handler.getMouseManager().quit) {
            System.exit(0); // If user selects exit, system exits
        } else if (handler.getMouseManager().help) { // If user selects help will be prompted with bellow dialog box
            String message = "Welcome to the Racing Game\n\n" +
                    "To control the cars you can use the following keys:\n" +
                    "1. Accelerate        - W and Arrow UP \n" +
                    "2. Brake                 -  S  and Arrow DOWN\n" +
                    "3. Left                     -  A  and Arrow LEFT\n" +
                    "4. Right                   -  D  and Arrow RIGHT\n\n" +
                    "Crashing Rules:\n" +
                    "Cars that touch the grass will stop and might require \n" +
                    "reverse driving to be able to return on the track.\n" +
                    "When two cars crash into each other the game will end.";
            // Using separate thread to main thread, for the flow of the moving background in the Menu
            // Dialog box reference: https://www.developer.com/java/create-java-dialog-boxes/
            Thread t = new Thread(new Runnable(){
                public void run(){
                    JOptionPane.showMessageDialog(null, message, "Help", 1);
                }});
            t.start();
            handler.getMouseManager().help = false; // Allows help button to be pressed again
        }
    }
    @Override
    public void render(Graphics g) {
        Color c1 = new Color(204,255,204); // Light green color for the title and menu
        g.setColor(c1);
        Graphics2D g2d = (Graphics2D) g.create();
        g.setFont(new Font("Times Roman", Font.BOLD, 50)); // Selected font bold and text size
        g.drawString("Racing Game - Part 2", 170, 100); // Title

        g.setFont(new Font("Times Roman", Font.BOLD, 30)); // Selecting font bold and text size for menu
        g2d.draw(playButton);
        g.drawString("Play", playButton.x + 15, playButton.y + 35); // Play Button
        g2d.draw(helpButton);
        g.drawString("Help", helpButton.x + 15, helpButton.y + 35); // Help button
        g2d.draw(quitButton);
        g.drawString("Quit", quitButton.x + 15, quitButton.y + 35); // Quit button
        // Moving cars in menu
        // The if loop runs until the cars are out of the screen
        // Then the else if creates a continuous loop as x becomes 0 each time returning it to the if loop above
        if (x <= 850) {
            g.drawImage(Media.playerBlue_move[4], x, 420, null);
            g.drawImage(Media.playerGreen_move[4], x, 280, null);
            x++;
        }
        else if (x > 850) {
            x = 0;
        }
        // Blur effect under title for both of the cars, intentionally having one longer than the other
        for (int i = 850; i > 120; i--) {
            g.drawImage(Media.playerGreen_move[12], i, 105,null); // Printing the same image over itself very close together
        }
        for (int i = 850; i > 220; i--) {
            g.drawImage(Media.playerBlue_move[12], i, 140,null); // Printing the same image over itself very close together
        }
    }
}