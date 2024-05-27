import java.awt.*;
public abstract class Maneuvering {
    protected RaceGround race;
    protected float x,y; // Float for smooth movement of car
    protected int width, height;
    protected Rectangle boundary; // Player boundary for collision detection
    public Maneuvering(RaceGround race, float x, float y, int width, int height) {
        this.race = race;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        boundary = new Rectangle(0,0,width,height);
    }
    public abstract void initiate(); // Initiate
    public abstract void render(Graphics g); // Apply graphics
    public float getX() {return x;}
    public float getY() {return y;}
}
