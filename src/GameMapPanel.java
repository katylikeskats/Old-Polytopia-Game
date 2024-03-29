import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Color;

//SOMETIMES GETS NULL POINTER ERRORS WHEN TRYING TO HARVEST THINGS OUTSIDE OF THE CITY's BONUDARIES
//ALSO SOMETIMES CAN HARVEST THINGS NOT WITHIN THE CITY

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

    private Map map;
    private Interactions interactions;
    static int tileDim; //Size of a tile (length)

    boolean[][] playerMask;
    Player player;

    private boolean unitMove;
    private int r = 0;
    private int c = 0;

    private static Image redTarget = Toolkit.getDefaultToolkit().getImage("assets/RedTarget.png");
    private static Image greyTarget = Toolkit.getDefaultToolkit().getImage("assets/GreyTarget.png");
    private static Image grassImage = Toolkit.getDefaultToolkit().getImage("assets/Grass.png");
    private static Image waterImage = Toolkit.getDefaultToolkit().getImage("assets/Water.png");
    private static Image mountainImage = Toolkit.getDefaultToolkit().getImage("assets/Mountain.png");
    private static Image cityImage = Toolkit.getDefaultToolkit().getImage("assets/ImperiusCity1.png");
    private static Image animalImage = Toolkit.getDefaultToolkit().getImage("assets/Animal.png");
    private static Image fishImage = Toolkit.getDefaultToolkit().getImage("assets/Fish.png");
    private static Image fruitImage = Toolkit.getDefaultToolkit().getImage("assets/Fruit.png");
    private static Image treeImage = Toolkit.getDefaultToolkit().getImage("assets/Tree.png");
    private static Image cropImage = Toolkit.getDefaultToolkit().getImage("assets/Crop.png");
    private static Image whaleImage = Toolkit.getDefaultToolkit().getImage("assets/Whale.png");
    private static Image starImage = Toolkit.getDefaultToolkit().getImage("assets/Star.png");
    private static Image cloudImage = Toolkit.getDefaultToolkit().getImage("assets/Cloud.png");

    Color grey = new Color(100,100,100);

    GameMapPanel(Map map, int height, boolean[][] mask, Player player) {
        this.map = map;
        interactions = new Interactions(map);

        this.setBackground(Color.black);
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
        if (unitSelected) { //If a unit is selected
            for (int i = -(map.getMap()[selectedR][selectedC].getUnit().getMovement()); i < map.getMap()[selectedR][selectedC].getUnit().getMovement() + 1; i++){
                for (int j = -(map.getMap()[selectedR][selectedC].getUnit().getMovement()); j < map.getMap()[selectedR][selectedC].getUnit().getMovement() + 1; j++) {
                    if (interactions.validateMove(map.getMap()[selectedR][selectedC].getUnit(), map.getMap()[selectedR][selectedC].getUnit().getR() + i, map.getMap()[selectedR][selectedC].getUnit().getC() + j)) {
                        g.drawImage(greyTarget, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    } else if (map.getMap()[map.getMap()[selectedR][selectedC].getUnit().getR() + i][map.getMap()[selectedR][selectedC].getUnit().getC() + j].getUnit() != null){
                        g.drawImage(redTarget, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    }
                }
            }
            if (unitMove) { //If the player selected the unit, then clicked on a different tile
                //if (validation using interactions) {
                //Part of code that moves the unit or does the attack stuff
                unitSelected = false; //Reset to say no unit is selected
                //}
                unitMove = false;
            }
        }

        //Doing resource stuff
        if (resourceSelected) {
            //OptionsPanel.showResource = true;
            //GameFrame.displayOptions = true;
            //if (((selectedR2-1) == selectedR) && (selectedC2 == selectedC)) {
            //Subtract from player's currency and increase population of the city that it belongs to
            //Yea maybe this stuff should be in a different class and use that class's methods
            //}
            //} else set resourceSelected to not
        } //else {
        //GameFrame.displayOptions = false;
        //}

        if (resourceHarvest) {
            //System.out.println(selectedR + ", " + selectedC);
            if (interactions.harvestItem(player, selectedR, selectedC)) {
                resourceHarvest = false;
                resourceSelected = false;
                GameFrame.displayOptions = false;
            }
        }

        //Doing city stuff
        if (citySelected) {
        }
        //System.out.println(resourceSelected+" "+citySelected);
    }



    //Straight from template as well
//Put in thing to ensure that they can click on neutral space
    private class MyMouseListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {

        }

        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int option = 0;
            if ((x <= tileDim * (map.getMap().length)) && (x >= 0) && (y <= tileDim * (map.getMap().length)) && (y >= 0)) {
                r = (int) Math.floor((((double) y) ) / ((double) tileDim));
                c = (int) Math.floor((((double) x) ) / ((double) tileDim));
                if (unitSelected || citySelected || resourceSelected) {
                    option = interactions.displayOptions(r, c, true, playerMask); //Has to pass in not just unitSelected, but whatever is being selected
                } else {
                    option = interactions.displayOptions(r, c, false, playerMask);
                }
            }
            if (option == 1) {
                resourceSelected = false;
                citySelected = false;
                GameFrame.displayOptions = false;
                resourceHarvest = false;

                if (!unitSelected) {
                    unitSelected = true;
                } else {
                    unitSelected = false;
                }
            } else if (option == 2) {
                resourceSelected = false;
                unitSelected = false;
                GameFrame.displayOptions = false;
                OptionsPanel.showUnit = false;
                OptionsPanel.showResource = false;
                resourceHarvest = false;

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
                GameFrame.displayOptions = false;
                OptionsPanel.showResource = true;
            }
            if (option < 4) {
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
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

    } //end of mouselistener

}