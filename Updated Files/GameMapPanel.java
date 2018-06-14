//MAKE DISPLAY FOR UNITS THAT MOVED ALREADY (lighter coloured image)
//IMPORTANT: Sometimes the unit is created but doesn't display? It's on the map though - when you select the spot you still select a unit (just doesn't show)
 //Clarification: Seems to be a problem with the top right city only?
 //AIGHT FIX THIS SHIT

import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Color;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class GameMapPanel extends JPanel{
  
    private static int selectedR;
    private static int selectedC;
    private static int selectedR2;
    private static int selectedC2;
    
    private static boolean unitSelected;
    private static boolean citySelected;
    private static boolean resourceSelected;
    private static boolean waterSelected; //To mark if a water tile (without fish or whales) is selected (for port building)
    
    private static boolean resourceHarvest; 
    private static boolean trainUnit;
    private static String trainUnitType;
    
    private static boolean drawSwords;
    
    private Map map;
    private Interactions interactions;
    private static int tileDim; //Size of a tile (length)
    
    private boolean[][] playerMask;
    private Player player;

    private static boolean unitMove;

    private static Image redTarget = Toolkit.getDefaultToolkit().getImage("RedTarget.png");
    private static Image greyTarget = Toolkit.getDefaultToolkit().getImage("GreyTarget.png");
    private static Image grassImage = Toolkit.getDefaultToolkit().getImage("Grass.png");
    private static Image waterImage = Toolkit.getDefaultToolkit().getImage("Water.png");
    private static Image mountainImage = Toolkit.getDefaultToolkit().getImage("Mountain.png");
    private static Image cityImage = Toolkit.getDefaultToolkit().getImage("ImperiusCity1.png");
    private static Image animalImage = Toolkit.getDefaultToolkit().getImage("Animal.png");
    private static Image fishImage = Toolkit.getDefaultToolkit().getImage("Fish.png");
    private static Image fruitImage = Toolkit.getDefaultToolkit().getImage("Fruit.png");
    private static Image treeImage = Toolkit.getDefaultToolkit().getImage("Tree.png");
    private static Image cropImage = Toolkit.getDefaultToolkit().getImage("Crop.png");
    private static Image whaleImage = Toolkit.getDefaultToolkit().getImage("Whale.png");
    private static Image starImage = Toolkit.getDefaultToolkit().getImage("Star.png");
    private static Image cloudImage = Toolkit.getDefaultToolkit().getImage("Cloud.png");
    private static Image swordsImage = Toolkit.getDefaultToolkit().getImage("Swords.png");
    
    //private static Image[] warriorImages = new Image[4];
    
    private static Image warriorImage1 = Toolkit.getDefaultToolkit().getImage("Warrior1.png");
    //private static Image warriorImage2 = Toolkit.getDefaultToolkit().getImage("Warrior2.png");
    //private static Image warriorImage3 = Toolkit.getDefaultToolkit().getImage("Warrior3.png");
    //private static Image warriorImage4 = Toolkit.getDefaultToolkit().getImage("Warrior4.png");
    //private static Image warriorImage5 = Toolkit.getDefaultToolkit().getImage("Warrior5.png");
    
    Color grey = new Color(100,100,100);

    GameMapPanel(Map map, int height, boolean[][] mask, Player player) {
        this.map = map;
        interactions = new Interactions(map);

        setSize(new Dimension(height, height));
        tileDim = (height/map.getMap().length);

        MapMouseListener mouseListener = new MapMouseListener();
        this.addMouseListener(mouseListener);
        
        playerMask = mask;
        this.player = player;
        
        mouseListener.setMap(map);
        mouseListener.setMask(playerMask);

    }


        public void paintComponent(Graphics g) {
            int currX = 1; //For later (like menu option shit)
            int currY = 1; //For later
            super.paintComponent(g); //required
            setDoubleBuffered(true); //What is this

            //Tile types
            for (int i = 0; i < map.getMap().length; i++) {
                for (int j = 0; j < map.getMap().length; j++) {
                    if (map.getMap()[i][j].getTerrain() instanceof Water) {
                      g.drawImage(waterImage, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    } else if (map.getMap()[i][j].getTerrain() instanceof Grass) {
                      g.drawImage(grassImage, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    } else if (map.getMap()[i][j].getTerrain() instanceof Mountain) {
                      g.drawImage(mountainImage, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    }
                    if (map.getMap()[i][j].getCity() != null) {
                      g.drawImage(cityImage, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    }
                }
            }

            //Resources
            for (int i = 0; i < map.getMap().length; i++) {
                for (int j = 0; j < map.getMap().length; j++) {
                    if (map.getMap()[i][j].getResource() instanceof Fruit) {
                        g.drawImage(fruitImage, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    } else if (map.getMap()[i][j].getResource() instanceof Crop) {
                        g.drawImage(cropImage, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    } else if (map.getMap()[i][j].getResource() instanceof Fish) {
                        g.drawImage(fishImage, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    } else if (map.getMap()[i][j].getResource() instanceof Forest) {
                        g.drawImage(treeImage, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    } else if (map.getMap()[i][j].getResource() instanceof Animal) {
                        g.drawImage(animalImage, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    } else if (map.getMap()[i][j].getResource() instanceof Whale) {
                      g.drawImage(whaleImage, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    }
                }
            }
            
            //Units
            //PUT IN ALL THE OTHER UNIT DISPLAYS
            for (int i = 0; i < map.getMap().length; i++) {
              for (int j = 0; j < map.getMap().length; j++) {
                if (map.getMap()[i][j].getUnit() instanceof Warrior) {
                  if (player.getTribe() == 0) {
                    g.drawImage(warriorImage1, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                  } else {
                    g.drawImage(warriorImage1, (tileDim*j), (tileDim*i), tileDim, tileDim, this); //Gonna be the other ones
                  }
                }
              }
            }
            
            //City borders
            //Have to change this if we're doing border expansion
            for (int i = 0; i < map.getMap().length; i++) {
              for (int j = 0; j < map.getMap().length; j++) {
                if (map.getMap()[i][j].getCity() != null) {
                  if (map.getMap()[i][j].getCity().getTribe() != 5) {
                    if (map.getMap()[i][j].getCity().getTribe() == player.getTribe()) {
                      g.setColor(Color.YELLOW);
                    } else {
                      g.setColor(Color.RED);
                    }
                    g.drawRect((tileDim * (j - map.getMap()[i][j].getCity().getCityRadius())), (tileDim * (i - map.getMap()[i][j].getCity().getCityRadius())), (tileDim * ((map.getMap()[i][j].getCity().getCityRadius() * 2) + 1)), (tileDim * ((map.getMap()[i][j].getCity().getCityRadius() * 2) + 1)));
                  }
                }
              }
            }
            
            //City pop/max unit num indicators
            //Works for max populations UP TO 5 ONLY!!!!
            for (int i = 0; i < map.getMap().length; i++) {
                for (int j = 0; j < map.getMap().length; j++) {
                  if (map.getMap()[i][j].getCity() != null) {
                    g.setColor(Color.BLACK);
                    for (int a = 0; a < map.getMap()[i][j].getCity().getMaxPop(); a++) { //Display the boxes for population
                      if (((map.getMap()[i][j].getCity().getMaxPop() == 3) && (a == 2)) || ((map.getMap()[i][j].getCity().getMaxPop() > 3) && (a == 3))) {
                        g.drawRect((int)(((tileDim*j)+(tileDim/2)-(((double)(map.getMap()[i][j].getCity().getLevel()+1)*((double)(tileDim)/4))/2))+(a*((double)(tileDim)/4)))+1, ((tileDim*(i+1))-(tileDim/5)), (int)(Math.round((double)(tileDim)/4))-1, (tileDim/5)+(tileDim/10));
                      } else {
                        g.drawRect((int)(((tileDim*j)+(tileDim/2)-(((double)(map.getMap()[i][j].getCity().getLevel()+1)*((double)(tileDim)/4))/2))+(a*((double)(tileDim)/4))), ((tileDim*(i+1))-(tileDim/5)), (int)(Math.round((double)(tileDim)/4)), (tileDim/5)+(tileDim/10));
                      }
                    }
                    //g.drawRect((int)((tileDim*j)+(tileDim/2)-(((double)(map.getMap()[i][j].getCity().getLevel()+1)*((double)(tileDim)/4))/2)), ((tileDim*(i+1))-(tileDim/5)), (int)((double)(map.getMap()[i][j].getCity().getLevel()+1)*((double)(tileDim)/4)), ((tileDim/5)+(tileDim/10)));
                    g.setColor(Color.BLUE);
                    for (int a = 0; a < map.getMap()[i][j].getCity().getMaxPop(); a++) { //Display the current population (filled in bars)
                      if (a >= map.getMap()[i][j].getCity().getCurrPop()) {
                        g.setColor(grey);
                      }
                      if (((map.getMap()[i][j].getCity().getMaxPop() == 3) && (a == 2)) || ((map.getMap()[i][j].getCity().getMaxPop() > 3) && (a == 3))) {
                        g.fillRect((int)(((tileDim*j)+(tileDim/2)-(((double)(map.getMap()[i][j].getCity().getLevel()+1)*((double)(tileDim)/4))/2))+(a*((double)(tileDim)/4)))+2, ((tileDim*(i+1))-(tileDim/5))+1, (int)(Math.round((double)(tileDim)/4))-2, ((tileDim/5)+(tileDim/10))-1);
                      } else {
                        g.fillRect((int)(((tileDim*j)+(tileDim/2)-(((double)(map.getMap()[i][j].getCity().getLevel()+1)*((double)(tileDim)/4))/2))+(a*((double)(tileDim)/4)))+1, ((tileDim*(i+1))-(tileDim/5))+1, (int)(Math.round((double)(tileDim)/4))-1, ((tileDim/5)+(tileDim/10))-1);
                      }
                    }
                    g.setColor(Color.WHITE); //Draws white circles if they aren't on a blue filled rectangle
                    for (int a = 0; a < map.getMap()[i][j].getCity().getCurrUnits(); a++) {
                      if (a >= map.getMap()[i][j].getCity().getCurrPop()) {
                        g.setColor(Color.BLACK); //If there are more units than there is current population, start drawing black circles (since the background will now be black)
                      }
                      g.fillOval((int)(((tileDim*j)+(tileDim/2)-(((double)(map.getMap()[i][j].getCity().getLevel()+1)*((double)(tileDim)/4))/2))+(a*((double)(tileDim)/4)))+4, ((tileDim*(i+1))-(tileDim/5))+4, (int)(Math.round((double)(tileDim)/4))-8, ((tileDim/5)+(tileDim/10))-8);
                    }
                    if (map.getMap()[i][j].getCity().isCapital()) {
                      g.drawImage(starImage, (int)((tileDim*j)+(tileDim/2)-(((double)(map.getMap()[i][j].getCity().getLevel()+1)*((double)(tileDim)/4))/2))-(int)(Math.round((double)(tileDim)/4)), ((tileDim*(i+1))-(tileDim/5)), (int)(Math.round((double)(tileDim)/4)), ((tileDim/5)+(tileDim/10)), this);
                    }
                  }
                }
            }
            
            //Cover stuff with mask
            for (int i = 0; i < map.getMap().length; i++) {
              for (int j = 0; j < map.getMap().length; j++) {
                if (playerMask[i][j]) {
                  g.drawImage(cloudImage, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                }
              }
            }

            
            //Display movement and attack targets for a selected unit
            //Now the targets display, but only within the city?
            if ((unitSelected) && !(map.getMap()[selectedR][selectedC].getUnit().getMoved())) { //If a unit is selected
                for (int i = -(map.getMap()[selectedR][selectedC].getUnit().getMovement()); i <= map.getMap()[selectedR][selectedC].getUnit().getMovement(); i++){
                    for (int j = -(map.getMap()[selectedR][selectedC].getUnit().getMovement()); j <= map.getMap()[selectedR][selectedC].getUnit().getMovement(); j++) {
                      if ((!((i == 0) && (j == 0))) && ((selectedR + i) >= 0) && ((selectedC + j) >= 0) && ((selectedR + i) < map.getMap().length) && ((selectedC + j) < map.getMap().length)) {
                        if (interactions.validateMove(map.getMap()[selectedR][selectedC].getUnit(), selectedR + i, selectedC + j, player, playerMask)) {
                            g.drawImage(greyTarget, (tileDim*(j + selectedC)), (tileDim*(i + selectedR)), tileDim, tileDim, this);
                            //Ensure that if attacking another unit, and the another unit is killed, the unit defeating it cannot move onto the tile unless it has tech required
                        } else if ((map.getMap()[selectedR + i][selectedC + j].getUnit() != null) && (interactions.validateAttack(map.getMap()[selectedR][selectedC].getUnit(), selectedR + i, selectedC + j, player))){
                            g.drawImage(redTarget, (tileDim*(j + selectedC)), (tileDim*(i + selectedR)), tileDim, tileDim, this);
                            //make it so it DOESNT DRAW RED TARGETS FOR FRIENDLY UNITS
                        }
                      }
                    }
                }
            }
            
            if (unitSelected) {
              //FOR UNIT OPTIONS DISPLAY (stats) if they've already moved
            }
            
            //UNIT MOVEMENT AND ATTACKING
            if ((unitMove) && !(map.getMap()[selectedR][selectedC].getUnit().getMoved())) { //If the player selected the unit, then clicked on a different tile
              //if the spot under newR, newC is a unit, validate attack with unit on selectedR, selectedC, and putting in selectedR2 and C2
              if (map.getMap()[selectedR2][selectedC2].getUnit() != null) {
                if (interactions.validateAttack(map.getMap()[selectedR][selectedC].getUnit(), selectedR2, selectedC2, player)) {
                  drawSwords = true;
                  map.getMap()[selectedR][selectedC].getUnit().setMoved(true); //Set the unit to having moved this turn
                  interactions.attack(map.getMap()[selectedR][selectedC].getUnit(), map.getMap()[selectedR2][selectedC2].getUnit());
                  if (map.getMap()[selectedR2][selectedC2].getUnit() == null) {
                    if (interactions.validateMove(map.getMap()[selectedR][selectedC].getUnit(), selectedR2, selectedC2, player, playerMask)) {
                      interactions.move(map.getMap()[selectedR][selectedC].getUnit(), selectedR2, selectedC2, playerMask); //Make the unit that just defeated another unit (if applicable) move onto that space
                    }
                  }
                }
              } else { //If the unit is to move (not attack)
                if (interactions.validateMove(map.getMap()[selectedR][selectedC].getUnit(), selectedR2, selectedC2, player, playerMask)) {
                  map.getMap()[selectedR][selectedC].getUnit().setMoved(true); //Set the unit to having moved this turn
                  interactions.move(map.getMap()[selectedR][selectedC].getUnit(), selectedR2, selectedC2, playerMask);
                }
                //} 
                //Else (no unit on newR, newC), validate move onto the new unit
              }
              //Reset these two after whatever is clicked on after unitMove is activated
              unitSelected = false; //Reset to say no unit is selected if the above is false (cannot attack)
              unitMove = false; //Reset to say no unit is going to be moved (attempted to) if above is false 
            } else if (unitMove) {
              unitSelected = false;
              unitMove = false;
            }
            
            
            if (trainUnit) {
              if (interactions.trainUnit(player, trainUnitType, selectedR, selectedC)) {
                trainUnit = false;
                citySelected = false;
                GameFrame.displayOptions = false;
              }
            }

            //If a resource is selected, and the button in the options panel was selected to confirm
            if (resourceHarvest) {
              if (interactions.harvestItem(player, selectedR, selectedC)) { //Check if the resource can be harvested (and harvest it if so)
                resourceHarvest = false; //Set it so that no resource is selected/to be harvested if one was just harvested
                resourceSelected = false;
                GameFrame.displayOptions = false; //Make the options panel disappear if a resource was just harvested
              }
            }
            
            //Display the icon symbolizing an attack if a unit is attacking another (until the player clicks somewhere else)
            if (drawSwords) {
              if (selectedR > 0) {
                g.drawImage(swordsImage, (selectedC * tileDim) + 10, ((selectedR - 1) * tileDim) + 10, tileDim - 20, tileDim - 20, this);
              } else {
                g.drawImage(swordsImage, (selectedC * tileDim) + 10, ((selectedR + 1) * tileDim) + 10, tileDim - 20, tileDim - 20, this);
              }
            }

        }
    
    public static int getSelectedR() {
      return selectedR;
    }
    
    public static int getSelectedC() {
      return selectedC;
    }
    
    public static int getSelectedR2() {
      return selectedR2;
    }
    
    public static int getSelectedC2() {
      return selectedC2;
    }
    
    public static boolean getUnitSelected() {
      return unitSelected;
    }

    public static boolean getResourceSelected() {
      return resourceSelected;
    }
    
    public static boolean getCitySelected() {
      return citySelected;
    }
    
    public static boolean getResourceHarvest() {
      return resourceHarvest;
    }
    
    public static boolean getTrainUnit() {
      return trainUnit;
    }
    
    public static String getTrainUnitType() {
      return trainUnitType;
    }
    
    public static boolean getDrawSwords() {
      return drawSwords;
    }
    
    public static int getTileDim() {
      return tileDim;
    }
    
    public static boolean getUnitMove() {
      return unitMove;
    }
    
    public static void setDrawSwords(boolean draw) {
      drawSwords = draw;
    }
    
    public static void setSelectedR(int r) {
      selectedR = r;
    }
    
    public static void setSelectedR2(int r2) {
      selectedR2 = r2;
    }
    
    public static void setSelectedC(int c) {
      selectedC = c;
    }
    
    public static void setSelectedC2(int c2) {
      selectedC2 = c2;
    }
    
    public static void setResourceSelected(boolean selected) {
      resourceSelected = selected;
    }
    
    public static void setUnitSelected(boolean selected) {
      unitSelected = selected;
    }
    
    public static void setCitySelected(boolean selected) {
      citySelected = selected;
    }
    
    public static void setResourceHarvest(boolean selected) {
      resourceHarvest = selected;
    }
    
    public static void setTrainUnit(boolean selected) {
      trainUnit = selected;
    }
    
    public static void setUnitMove(boolean selected) {
      unitMove = selected;
    }
    
    public static void setTrainUnitType(String type) {
      trainUnitType = type;
    }
    
    public static void setWaterSelected(boolean selected) {
      waterSelected = selected;
    }
    
    public static boolean getWaterSelected() {
      return waterSelected;
    }
    
}