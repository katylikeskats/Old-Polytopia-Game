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
    private static Image pic = Toolkit.getDefaultToolkit().getImage("assets/Tree.png");
    private static Image pic2 = Toolkit.getDefaultToolkit().getImage("assets/OumanjiHead.png");
    private static Image pic3 = Toolkit.getDefaultToolkit().getImage("assets/Animal.png");
    //Constructor - this runs first
    OptionsPanel(int height, int width) {
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width-50,Toolkit.getDefaultToolkit().getScreenSize().height-50);
        this.setBackground(new Color(87, 65, 200, 150));

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
            }
            if (showResource){
                g.drawString("Resource Options!", 880, 40);
                g.drawImage(pic3, 800, 0, 80,70, this);
            }
        }


}