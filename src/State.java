import java.awt.*;
public abstract class State {
    private static State currentState = null;
    public static void setState(State state){
        currentState = state;
    } // Set game state
    public static State getState(){
        return currentState;
    } // Get game state
    protected Handler handler;
    public State(Handler handler) {
        this.handler = handler;
    }
    public abstract void readUserAction(); // User key or mouse action reader, relating to driver crashing detection too
    public abstract void render(Graphics g);
}
