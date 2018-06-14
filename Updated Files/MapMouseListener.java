import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class MapMouseListener implements MouseListener {
  
  private Map map;
  private boolean[][] playerMask;
  
  public void mouseClicked(MouseEvent e) {
  }
  
  public void mousePressed(MouseEvent e) {
    GameMapPanel.setDrawSwords(false); //Whenever you click on something, don't draw (or stop drawing) the icon symbolizing a fighting interaction between two units
    int x = e.getX();
    int y = e.getY();
    int option = 0;
    if ((x <= GameMapPanel.getTileDim() * (map.getMap().length)) && (x >= 0) && (y <= GameMapPanel.getTileDim() * (map.getMap().length)) && (y >= 0)) {
      if (GameMapPanel.getUnitSelected()) {
        GameMapPanel.setSelectedR2((int) Math.floor((((double) y) ) / ((double) GameMapPanel.getTileDim())));
        GameMapPanel.setSelectedC2((int) Math.floor((((double) x) ) / ((double) GameMapPanel.getTileDim())));
      } else {
        GameMapPanel.setSelectedR((int) Math.floor((((double) y) ) / ((double) GameMapPanel.getTileDim())));
        GameMapPanel.setSelectedC((int) Math.floor((((double) x) ) / ((double) GameMapPanel.getTileDim())));
      }
      option = getOptions(GameMapPanel.getSelectedR(), GameMapPanel.getSelectedC(), GameMapPanel.getSelectedR2(), GameMapPanel.getSelectedC2(), GameMapPanel.getUnitSelected(), playerMask);
    }
    if (option == 1) {
      GameMapPanel.setResourceSelected(false);
      GameMapPanel.setCitySelected(false);
      GameFrame.displayOptions = false;
      GameMapPanel.setResourceHarvest(false);
      GameMapPanel.setTrainUnit(false);
      
      if (!GameMapPanel.getUnitSelected()) {
        GameMapPanel.setUnitSelected(true);
      }
    } else if (option == 2) {
      GameMapPanel.setResourceSelected(false);
      GameMapPanel.setUnitSelected(false);
      GameFrame.displayOptions = false;
      OptionsPanel.showUnit = false;
      OptionsPanel.showResource = false;
      GameMapPanel.setResourceHarvest(false);
      GameMapPanel.setTrainUnit(false);
      
      if (!GameMapPanel.getCitySelected()){
        GameMapPanel.setCitySelected(true);
        GameFrame.displayOptions = true;
        OptionsPanel.showUnit = true;
        
      } else {
        GameMapPanel.setCitySelected(false);
      }
      
    } else if (option == 3) {
      GameMapPanel.setUnitSelected(false);
      GameMapPanel.setCitySelected(false);
      GameFrame.displayOptions = false;
      OptionsPanel.showUnit = false;
      OptionsPanel.showResource = false;
      GameMapPanel.setResourceHarvest(false);
      GameMapPanel.setTrainUnit(false);
      
      if (!GameMapPanel.getResourceSelected()) {
        GameMapPanel.setResourceSelected(true);
        GameFrame.displayOptions = true;
        OptionsPanel.showResource = true;
      } else {
        GameMapPanel.setResourceSelected(false);
      }
    } else if (option == 4) {
      GameMapPanel.setUnitMove(true);
    } else if (option == 5) {
      GameMapPanel.setResourceSelected(false);
      GameMapPanel.setUnitSelected(false);
      GameMapPanel.setCitySelected(false);
      GameMapPanel.setResourceHarvest(false);
      GameMapPanel.setTrainUnit(false);
      GameFrame.displayOptions = false;
      OptionsPanel.showResource = true;
    } else if (option == 6) {
      GameMapPanel.setResourceSelected(false);
      GameMapPanel.setUnitSelected(false);
      GameMapPanel.setCitySelected(false);
      GameMapPanel.setResourceHarvest(false);
      GameMapPanel.setTrainUnit(false);
      OptionsPanel.showResource = false;
      OptionsPanel.showUnit = false;
      
      if (!GameMapPanel.getWaterSelected()) {
        GameMapPanel.setWaterSelected(true);
        GameFrame.displayOptions = true;
        OptionsPanel.showPort = true;
      } else {
        GameMapPanel.setWaterSelected(false);
        GameFrame.displayOptions = false;
        OptionsPanel.showPort = false;
      }
    }
  }
  
  public void mouseReleased(MouseEvent e) {
  }
  
  public void mouseEntered(MouseEvent e) {
  }
  
  public void mouseExited(MouseEvent e) {
  }
  
  private int getOptions(int r, int c, int r2, int c2, boolean unitSelected, boolean[][] mask){
        if (!unitSelected){
            if (!mask[r][c]) {
              if (map.getMap()[r][c].containsUnit()){
                return 1; //If nothing is selected, then a unit is clicked on
              } else if (map.getMap()[r][c].containsCity()){
                return 2; //If nothing is selected, then a city is clicked on
              } else if (map.getMap()[r][c].containsResource()){
                return 3; //If nothing is selected, then a resource is clicked on
              } else if (map.getMap()[r][c].getTerrain() instanceof Water) {
                return 6;
              } else {
                return 5; //If a blank spot is clicked on
              }
          } else {
            return 5; //If clouds are clicked on
          }
        } else {
          if ((!((r == r2) && (c == c2))) && (map.getMap()[r][c].containsUnit())) { //If, after a unit is selected, another unit is selected that is not itself
            return 4;
          } else if (map.getMap()[r][c].containsUnit()) { //If, after a unit is selected, the same tile is selected again
            if (map.getMap()[r][c].getCity() != null) {
              return 2; //Return that the city under the unit is to be selected if applicable
            } else if (map.getMap()[r][c].getResource() != null) {
              return 3; //Return that the resource under the unit is to be selected if applicable
            } else {
              return 5; //If nothing is under the unit that is selected twice, unselect it
            }
          } else { //If, after a unit is selected, an empty tile, a tile with a resource, or a tile with a city is selected (no unit)
            return 4; //If a blank spot or spot with a resource is clicked on (try to move on it) IF IT CANT MOVE THERE THE UNIT WILL BE UNSELECTED
          }
        }
    }
  
  public void setMap(Map map) {
    this.map = map;
  }
  
  public void setMask(boolean[][] mask) {
    playerMask = mask;
  }
  
} //end of mouselistener