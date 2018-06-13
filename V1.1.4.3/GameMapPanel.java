//MAKE DISPLAY FOR UNITS THAT MOVED ALREADY (lighter coloured image)
//IMPORTANT: Sometimes the unit is created but doesn't display? It's on the map though - when you select the spot you still select a unit (just doesn't show)
 //Clarification: Seems to be a problem with the top right city only?
//MAKE IT SO THAT IT CAN CLICK ON OTHER THINGS WHEN MOVING - deselect the unit after selecting smth else if moved is false

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
  
    static int selectedR;
    static int selectedC;
    static int selectedR2;
    static int selectedC2;
    
    static boolean unitSelected;
    static boolean citySelected;
    static boolean resourceSelected;
    
    static boolean resourceHarvest; 
    static boolean trainUnit;
    static String trainUnitType;
    
    private Map map;
    private Interactions interactions;
    static int tileDim; //Size of a tile (length)
    
    boolean[][] playerMask;
    Player player;

    private boolean unitMove;
    private int r = 0;
    private int c = 0;

    private static Image redTarget = Toolkit.getDefaultToolkit().getImage("RedTarget.png");
    private static Image greyTarget = Toolkit.getDefaultToolkit().getImage("GreyTarget.png");
    private static Image grassImage = Toolkit.getDefaultToolkit().getImage("Grass.png");
    private static Image waterImage = Toolkit.getDefaultToolkit().getImage("Water.jpg");
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

        MyMouseListener mouseListener = new MyMouseListener();
        this.addMouseListener(mouseListener);
        
        playerMask = mask;
        this.player = player;

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

            
            //CHECK FOR TARGET AND MENU SHIT for each unit clicked on?
            //Um either not checking the right tiles or the display is just WACK
            if ((unitSelected) && !(map.getMap()[selectedR][selectedC].getUnit().getMoved())) { //If a unit is selected
                for (int i = -(map.getMap()[selectedR][selectedC].getUnit().getMovement()); i <= map.getMap()[selectedR][selectedC].getUnit().getMovement(); i++){
                    for (int j = -(map.getMap()[selectedR][selectedC].getUnit().getMovement()); j <= map.getMap()[selectedR][selectedC].getUnit().getMovement(); j++) {
                      if (!((i == 0) && (j == 0))) {
                        if (interactions.validateMove(map.getMap()[selectedR][selectedC].getUnit(), map.getMap()[selectedR][selectedC].getUnit().getR() + i, map.getMap()[selectedR][selectedC].getUnit().getC() + j, player)) {
                            g.drawImage(greyTarget, (tileDim*(j + selectedC)), (tileDim*(i + selectedR)), tileDim, tileDim, this);
                            //Ensure that if attacking another unit, and the another unit is killed, the unit defeating it cannot move onto the tile unless it has tech required
                        } else if (map.getMap()[map.getMap()[selectedR][selectedC].getUnit().getR() + i][map.getMap()[selectedR][selectedC].getUnit().getC() + j].getUnit() != null){
                            g.drawImage(redTarget, (tileDim*(j + selectedC)), (tileDim*(i + selectedR)), tileDim, tileDim, this);
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
                  map.getMap()[selectedR][selectedC].getUnit().setMoved(true); //Set the unit to having moved this turn
                  interactions.attack(map.getMap()[selectedR][selectedC].getUnit(), map.getMap()[selectedR2][selectedC2].getUnit());
                  if (map.getMap()[selectedR2][selectedC2].getUnit() == null) {
                    if (interactions.validateMove(map.getMap()[selectedR][selectedC].getUnit(), selectedR2, selectedC2, player)) {
                      interactions.move(map.getMap()[selectedR][selectedC].getUnit(), selectedR2, selectedC2); //Make the unit that just defeated another unit (if applicable) move onto that space
                    }
                  }
                }
              } else { //If the unit is to move (not attack)
                if (interactions.validateMove(map.getMap()[selectedR][selectedC].getUnit(), selectedR2, selectedC2, player)) {
                  map.getMap()[selectedR][selectedC].getUnit().setMoved(true); //Set the unit to having moved this turn
                  interactions.move(map.getMap()[selectedR][selectedC].getUnit(), selectedR2, selectedC2);
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

        }



//Put in thing to ensure that they can click on neutral space
    private class MyMouseListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int option = 0;
            if ((x <= tileDim * (map.getMap().length)) && (x >= 0) && (y <= tileDim * (map.getMap().length)) && (y >= 0)) {
              if (unitSelected) {
                selectedR2 = (int) Math.floor((((double) y) ) / ((double) tileDim));
                selectedC2 = (int) Math.floor((((double) x) ) / ((double) tileDim));
              } else {
                selectedR = (int) Math.floor((((double) y) ) / ((double) tileDim));
                selectedC = (int) Math.floor((((double) x) ) / ((double) tileDim));
              }
                option = interactions.displayOptions(selectedR, selectedC, selectedR2, selectedC2, unitSelected, playerMask);
            }
            if (option == 1) {
                resourceSelected = false;
                citySelected = false;
                GameFrame.displayOptions = false;
                resourceHarvest = false;
                trainUnit = false;

                if (!unitSelected) {
                    unitSelected = true;
                } else {
                    unitSelected = false; //This may be redundant, since there'll be different shit
                }
            } else if (option == 2) {
                resourceSelected = false;
                unitSelected = false;
                GameFrame.displayOptions = false;
                OptionsPanel.showUnit = false;
                OptionsPanel.showResource = false;
                resourceHarvest = false;
                trainUnit = false;

                if (!citySelected){
                    citySelected = true;
                    GameFrame.displayOptions = true;
                    OptionsPanel.showUnit = true;

                } else {
                    citySelected = false;
                }

            } else if (option == 3) {
                unitSelected = false;
                citySelected = false;
                GameFrame.displayOptions = false;
                OptionsPanel.showUnit = false;
                OptionsPanel.showResource = false;
                resourceHarvest = false;
                trainUnit = false;

                if (!resourceSelected) {
                    resourceSelected = true; //Ok at least this part works
                    GameFrame.displayOptions = true;
                    OptionsPanel.showResource = true;
                } else {
                    resourceSelected = false;
                }
            } else if (option == 4) {
                unitMove = true;
            } else if (option == 5) {
                resourceSelected = false;
                unitSelected = false;
                citySelected = false;
                resourceHarvest = false;
                trainUnit = false;
                GameFrame.displayOptions = false;
                OptionsPanel.showResource = true;
            }
            /*
            if (option < 4) { //Unnecessary? Done earlier now
                selectedR = r;
                selectedC = c;
            } else if (option == 4) { //Should only be option 4 if it is in range? - as it is now clicking anywhere that's not in range will just deselect everything?
                if ((y == selectedR) && (x == selectedC) && unitSelected) { //If you select a tile with a unit on it for the second time
                    unitSelected = false; //Set the unit to not selected
                    option = interactions.displayOptions(((x / tileDim) - 1), ((y / tileDim) - 1), false, playerMask); //Get the options for whatever else is on the same tile
                    if (option == 2) {
                        citySelected = true; //If a city below the unit
                    } else if (option == 3) {
                        resourceSelected = true; //If a resource below the unit
                    }
                } else { //If they select a tile with a unit on it and then click on a different tile
                    selectedR2 = y;
                    selectedC2 = x;
                }
            }
            //System.out.println(x+", "+y);
            */
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

    } //end of mouselistener

}