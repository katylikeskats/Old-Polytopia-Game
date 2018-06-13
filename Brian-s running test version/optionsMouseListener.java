import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class optionsMouseListener implements MouseListener {
  
  public void mouseClicked(MouseEvent e) {
  }
  
  public void mousePressed(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    if ((x > 1000) && (x < 1040) && (y > 20) && (y < 60) && (GameMapPanel.resourceSelected)) {
      GameMapPanel.resourceHarvest = true;
    } else if ((x > 980) && (x < 1040) && (y > 5) && (y < 65) && (GameMapPanel.citySelected)) {
      GameMapPanel.trainUnit = true;
      GameMapPanel.trainUnitType = "Warrior";
      System.out.println("Warrior");
    }
  }
  
  public void mouseReleased(MouseEvent e) {
  }
  
  public void mouseEntered(MouseEvent e) {
  }
  
  public void mouseExited(MouseEvent e) {
  }
  
  
}