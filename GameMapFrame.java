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
    static int tileDim;
    static int selectedR;
    static int selectedC;

    private static Image redTarget = Toolkit.getDefaultToolkit().getImage("RedTarget.png");
    private static Image greyTarget = Toolkit.getDefaultToolkit().getImage("GreyTarget.png");

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
            int currX = 1;
            int currY = 1;
            super.paintComponent(g); //required
            setDoubleBuffered(true); //What is this

            for (int i = 0; i < map.getMap().length; i++) {
                for (int j = 0; j < map.getMap().length; j++) {
                    g.setColor(Color.black);
                    if (map.getMap()[i][j].getTerrain() instanceof Water) {
                        g.setColor(Color.blue);
                    } if (map.getMap()[i][j].getTerrain() instanceof Grass) {
                        g.setColor(Color.green);
                    } if (map.getMap()[i][j].getCity() != null) {
                        g.setColor(Color.red);
                    }
                    g.fillRect((tileDim*j), (tileDim*i), tileDim, tileDim);
                }
            }

            //CHECK FOR TARGET AND MENU SHIT for each unit clicked on?
            //if (filler) {
            for (int i = -(map.getMap()[selectedR][selectedC].getUnit().getMovement()); i < map.getMap()[selectedR][selectedC].getUnit().getMovement() + 1; i++){
                for (int j = -(map.getMap()[selectedR][selectedC].getUnit().getMovement()); j < map.getMap()[selectedR][selectedC].getUnit().getMovement() + 1; j++) {

                    if (interactions.validateMove(map.getMap()[selectedR][selectedC].getUnit(), map.getMap()[selectedR][selectedC].getUnit().getR() + i, map.getMap()[selectedR][selectedC].getUnit().getC() + j)) {
                        g.drawImage(greyTarget, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    } else if (map.getMap()[map.getMap()[selectedR][selectedC].getUnit().getR() + i][map.getMap()[selectedR][selectedC].getUnit().getC() + j].getUnit() != null){
                        g.drawImage(redTarget, (tileDim*j), (tileDim*i), tileDim, tileDim, this);
                    }

                }
            }
            //}

        }
    }

    //Straight from template as well
    //Put in thing to ensure that they can click on neutral space
    private class MyMouseListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            //add the thing from git
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
