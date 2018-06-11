import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class OptionsMouseListener implements MouseListener {

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if ((x > 1000) && (x < 1040) && (y > 20) && (y < 60) && (GameMapPanel.resourceSelected)) {
            GameMapPanel.resourceHarvest = true;
            //System.out.println("Action done");
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }


}