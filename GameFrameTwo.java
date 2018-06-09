/**
 * This template can be used as reference or a starting point
 * for your final summative project
 * @author Mangat
 **/

//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.FontMetrics;
//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class GameFrameTwo extends JFrame {

    //class variable (non-static)
    static double x, y;
    static GameAreaPanel gamePanel;
    private int maxX;
    private int maxY;
    String name = "";
    int length = 0;
    //Constructor - this runs first
    GameFrameTwo() {

        super("My Game");
        //configure the window
        maxY = Toolkit.getDefaultToolkit().getScreenSize().height-50; //-50 so that the bottom appbar doesn't get in the way
        maxX = (int)Math.round(1.33*maxY);
        this.setSize(maxX, maxY);

        this.setLocationRelativeTo(null); //start the frame in the center of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable (false);

        //this.setUndecorated(true);  //Set to true to remove title bar
        //this.setBackground(new Color(108, 103, 99, 150));
//new Color(87, 65, 200, 150)
        //Set up the game panel (where we put our graphics)
        gamePanel = new GameAreaPanel();
        gamePanel.setBackground(new Color(0, 0, 0, 135));

        this.add(new GameAreaPanel());

        MyKeyListener keyListener = new MyKeyListener();
        this.addKeyListener(keyListener);

        MyMouseListener mouseListener = new MyMouseListener();
        this.addMouseListener(mouseListener);

        //this.requestFocusInWindow(); //make sure the frame has focus

        this.setVisible(true);

        //Start the game loop in a separate thread
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop
        t.start();

    } //End of Constructor

    //the main gameloop - this is where the game state is updated
    public void animate() {
        while(true){
            try{ Thread.sleep(10);} catch (Exception exc){}
            this.repaint();
        }
    }

    /** --------- INNER CLASSES ------------- **/

    // Inner class for the the game area - This is where all the drawing of the screen occurs
    private class GameAreaPanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g); //removed to keep transparent panel
            Font font1 = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
            FontMetrics fontMetrics = g.getFontMetrics(font1);
            Image background = Toolkit.getDefaultToolkit().getImage("assets/background3.png");
            g.drawImage(background,0,0,null);
            setDoubleBuffered(true);
            g.setColor(new Color(255, 128, 233)); //There are many graphics commands that Java can use
            g.fillRect(0, 200, maxX, 30); //notice the x,y variables that we control from our animate method
            g.setColor(new Color(255, 255, 255));
            g.setFont(font1);
            g.drawString(name.toLowerCase(), (maxX-fontMetrics.stringWidth(name.toLowerCase()))/2, 220);
        }
    }

    // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
    private class MyKeyListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (!(KeyEvent.getKeyText(e.getKeyCode()).equals("Backspace")) && !(KeyEvent.getKeyText(e.getKeyCode()).equals("Space")) && !(KeyEvent.getKeyText(e.getKeyCode()).equals("Shift"))) {
                name = name + KeyEvent.getKeyText(e.getKeyCode());
            } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("Backspace")) {
                name = name.substring(0, name.length() - 1);
            } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("Space")) {
                name = name + " ";
            }
        }

        public void keyReleased(KeyEvent e) {
        }
    } //end of keyboard listener

    // -----------  Inner class for the keyboard listener - This detects mouse movement & clicks and runs the corresponding methods
    private class MyMouseListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            System.out.println("Mouse Clicked");
            System.out.println("X:"+e.getX() + " y:"+e.getY());
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

    public static void main(String[] args){
        new GameFrameTwo();
    }
}