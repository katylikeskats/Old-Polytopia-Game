import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class GameMapFrame extends JFrame {
  
  static double x,y;
  static GameAreaPanel gamePanel;
  private Map map;
  private Interactions interactions;
  static int tileDim; //Size of a tile (length)
  static int selectedR;
  static int selectedC;
  static int selectedR2;
  static int selectedC2;
  static boolean unitSelected;
  static boolean citySelected;
  static boolean resourceSelected;
  private boolean unitMove;
  private int r = 0;
  private int c = 0;
  
  private static Image redTarget = Toolkit.getDefaultToolkit().getImage("RedTarget.png");
  private static Image greyTarget = Toolkit.getDefaultToolkit().getImage("GreyTarget.png");
  private static Image greenCheck = Toolkit.getDefaultToolkit().getImage("transparent-green-checkmark-hi.png");
  private static Image grassImage = Toolkit.getDefaultToolkit().getImage("Grass.png");
  private static Image waterImage = Toolkit.getDefaultToolkit().getImage("Water.png");
  private static Image cityImage = Toolkit.getDefaultToolkit().getImage("City.png");
  private static Image animalImage = Toolkit.getDefaultToolkit().getImage("Animal.png");
  private static Image fishImage = Toolkit.getDefaultToolkit().getImage("Fish.png");
  private static Image fruitImage = Toolkit.getDefaultToolkit().getImage("Fruit.png");
  private static Image treeImage = Toolkit.getDefaultToolkit().getImage("Tree.png");
  private static Image cropImage = Toolkit.getDefaultToolkit().getImage("Crop.png");
  
  GameMapFrame(Map map) {
    super("Polytopia");
    this.map = map;
    interactions = new Interactions(map);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
    tileDim = ((Toolkit.getDefaultToolkit().getScreenSize().height)/map.getMap().length);
    
    gamePanel = new GameAreaPanel();
    this.add(new GameAreaPanel());
    
    MyMouseListener mouseListener = new MyMouseListener();
    this.addMouseListener(mouseListener);
    
    //Start the game loop in a separate thread
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop
    t.start();
  }
  
  //the main gameloop - this is where the game state is updated
  public void animate() { //Change to suit
    
    while(true){
      //don't have anything unless in an if statement (repaint if mouseListener detects anything)
      this.repaint();
    }
  }
  
  //Straight from template rn
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  private class GameAreaPanel extends JPanel {
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
        //if () { //Check if the player has enough resources AND if it is in range of a city
        g.drawImage(greenCheck, (tileDim*(selectedC-1)), (tileDim*selectedR), tileDim, tileDim, this); //Show the green check below the resource selected to confirm
        if (((selectedR2-1) == selectedR) && (selectedC2 == selectedC)) {
          //Subtract from player's currency and increase population of the city that it belongs to
          //Yea maybe this stuff should be in a different class and use that class's methods
        }
        //} else set resourceSelected to not
      }
      
      //Doing city stuff
      if (citySelected) {
        //Display menu options first (troops to train), as well as showing city level
      }
      
    }
  }
  
  //Straight from template as well
  //Put in thing to ensure that they can click on neutral space
  private class MyMouseListener implements MouseListener {
    
    public void mouseClicked(MouseEvent e) {
      int x = e.getX();
      int y = e.getY();
      int option = 0;
      if ((x <= tileDim*(map.getMap().length)) && (x >= 0) && (y <= tileDim*(map.getMap().length)) && (y >= 0)) {
        r = (int)(Math.round(((double)y)/((double)tileDim)));
        c = (int)(Math.round(((double)x)/((double)tileDim)));
        option = interactions.displayOptions(r, c, unitSelected); //Has to pass in not just unitSelected, but whatever is being selected
      }
      if (option == 1) {
        if (!unitSelected) {
          unitSelected = true;
        } else {
          unitSelected = false;
        }
      } else if (option == 2) {
        citySelected = true;
      } else if (option == 3) {
        if (!resourceSelected) {
          resourceSelected = true; //Ok at least this part works
        } else {
          unitSelected = false;
        }
      } else if (option == 4) {
        unitMove = true;
      }
      if (option < 4) {
        selectedR = r;
        selectedC = c;
      } else if (option == 4) { //Should only be option 4 if it is in range? - as it is now clicking anywhere that's not in range will just deselect everything?
        if ((y == selectedR) && (x == selectedC) && unitSelected) { //If you select a tile with a unit on it for the second time
          unitSelected = false; //Set the unit to not selected
          option = interactions.displayOptions(((x/tileDim)-1), ((y/tileDim)-1), false); //Get the options for whatever else is on the same tile
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
    }
    
    public void mousePressed(MouseEvent e) {
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
    
  } //end of mouselistener
  
  public static void main(String[] args) {
    GameMapFrame window = new GameMapFrame(new Map(20)); //Just for test
    window.setVisible(true);
  }
}
