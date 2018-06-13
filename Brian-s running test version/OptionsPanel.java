//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class OptionsPanel extends JPanel {

    //class variable (non-static)
    static double x, y;
    static boolean showUnit;
    static boolean showResource;
    private static Image pic = Toolkit.getDefaultToolkit().getImage("Tree.png");
    private static Image pic2 = Toolkit.getDefaultToolkit().getImage("OumanjiHead.png");
    private static Image pic3 = Toolkit.getDefaultToolkit().getImage("Animal.png");
    private static Image greenCheck = Toolkit.getDefaultToolkit().getImage("Checkmark.png");
    Color grey = new Color(100, 100, 100);
    //Constructor - this runs first
    OptionsPanel(int height, int width) {
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width-50,Toolkit.getDefaultToolkit().getScreenSize().height-50);
        this.setBackground(new Color(87, 65, 200, 150));

        optionsMouseListener optionsListener = new optionsMouseListener();
        this.addMouseListener(optionsListener);
        
    } //End of Constructor


    /** --------- INNER CLASSES ------------- **/

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(pic,500,20,100,100,this);
            setDoubleBuffered(true);
            g.drawString("This is the options panel sorry its so ugly I got sick so I'll update it later June 9", 300,20);
            if (showUnit){
                g.drawString("Unit Options!", 880, 40);
                g.drawImage(pic2, 800, 15, 70,70, this);
                g.setColor(grey);
                g.fillRect(980, 5, 60, 60); //Warrior since every city can train a warrior
                g.setColor(Color.WHITE);
                g.drawString("Warrior, " + Warrior.getCost() + "*", 983, 40);
            }
            if (showResource){
                g.drawString("Harvest Resource?", 880, 40);
                g.drawImage(pic3, 800, 0, 80,70, this);
                g.setColor(grey);
                g.fillRect(990, 10, 60, 60);
                g.drawImage(greenCheck, 1000, 20, 40, 40, this);
            }
        }


}