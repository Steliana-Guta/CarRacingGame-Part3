import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class MouseControl implements MouseListener {
    public boolean play, quit, help; // Bool variable to look for clicked buttons
    public MouseControl() {}
    public void mousePressed(MouseEvent e) {
        int mx = e.getX(); // Variables for button positions
        int my = e.getY(); // Variables for button positions
        if(mx >= 850 / 2 - 50  && mx <= 850 / 2 + 50 ) {
            if(my >= 200 && my <= 250) {play = true;} // Play Button
        }
        if(mx >= 850 / 2 - 50  && mx <= 850 / 2 + 50 ) {
            if(my >= 350 && my <= 400) {help = true;} // Help button
        }
        if(mx >= 850 / 2 - 50  && mx <= 850 / 2 + 50 ) {
            if(my >= 500 && my <= 550) {quit = true;} // Quit button
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }
}
