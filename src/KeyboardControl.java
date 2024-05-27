import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyboardControl implements KeyListener {
    private boolean[] key;
    public boolean upKey, downKey, leftKey, rightKey, upArrow, downArrow, leftArrow, rightArrow; // All key controls
    public KeyboardControl() {
        key = new boolean[256];
    }
    public void keys() {
        // Left car (Green)
        upKey = key[KeyEvent.VK_W];
        downKey = key[KeyEvent.VK_S];
        leftKey = key[KeyEvent.VK_A];
        rightKey = key[KeyEvent.VK_D];
        // Right car (Blue)
        upArrow = key[KeyEvent.VK_UP];
        downArrow = key[KeyEvent.VK_DOWN];
        leftArrow = key[KeyEvent.VK_LEFT];
        rightArrow = key[KeyEvent.VK_RIGHT];
    }
    @Override
    public void keyPressed(KeyEvent e) {
        key[e.getKeyCode()] = true;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        key[e.getKeyCode()] = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
