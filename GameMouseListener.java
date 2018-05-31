import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class GameMouseListener implements MouseListener {
    private int maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    private int gridScreenRatio = maxY/Utilities.dimensions;
    private Interactions interactions;

    public GameMouseListener(Interactions interactions){
        this.interactions = interactions;
    }

    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        if ((x <= maxY) && (x >= 0) && (y <= maxY) && (y >= 0)) {
            int r = (x/gridScreenRatio)-1;
            int c = (y/gridScreenRatio)-1;
            interactions.displayOptions(r, c);
        }

    }
    public void mousePressed(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }

}

