import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
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
    private Map map;
    private Interactions interactions;
    static int tileDim; //Size of a tile (length)

    private boolean unitMove;
    private int r = 0;
    private int c = 0;

    private static Image redTarget = Toolkit.getDefaultToolkit().getImage("assets/RedTarget.png");
    private static Image greyTarget = Toolkit.getDefaultToolkit().getImage("assets/GreyTarget.png");
    private static Image greenCheck = Toolkit.getDefaultToolkit().getImage("assets/Checkmark.png");
    private static Image grassImage = Toolkit.getDefaultToolkit().getImage("assets/Grass.png");
    private static Image waterImage = Toolkit.getDefaultToolkit().getImage("assets/Water.jpg");
    private static Image cityImage = Toolkit.getDefaultToolkit().getImage("assets/ImperiusCity1.png");
    private static Image animalImage = Toolkit.getDefaultToolkit().getImage("assets/Animal.png");
    private static Image fishImage = Toolkit.getDefaultToolkit().getImage("assets/Fish.png");
    private static Image fruitImage = Toolkit.getDefaultToolkit().getImage("assets/Fruit.png");
    private static Image treeImage = Toolkit.getDefaultToolkit().getImage("assets/Tree.png");
    private static Image cropImage = Toolkit.getDefaultToolkit().getImage("assets/Crop.png");

    GameMapPanel(Map map, int height) {
        this.map = map;
        interactions = new Interactions(map);

        setSize(new Dimension(height, height));
        tileDim = (height/map.getMap().length);

        MyMouseListener mouseListener = new MyMouseListener();
        this.addMouseListener(mouseListener);

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
                    option = interactions.displayOptions(r, c, true); //Has to pass in not just unitSelected, but whatever is being selected
                } else {
                    option = interactions.displayOptions(r, c, false);
                }
            }
            if (option == 1) {
                resourceSelected = false;
                citySelected = false;
                GameFrame.displayOptions = false;

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
                GameFrame.displayOptions = false;
            }
            if (option < 4) {
                selectedR = r;
                selectedC = c;
            } else if (option == 4) { //Should only be option 4 if it is in range? - as it is now clicking anywhere that's not in range will just deselect everything?
                if ((y == selectedR) && (x == selectedC) && unitSelected) { //If you select a tile with a unit on it for the second time
                    unitSelected = false; //Set the unit to not selected
                    option = interactions.displayOptions(((x / tileDim) - 1), ((y / tileDim) - 1), false); //Get the options for whatever else is on the same tile
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