import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
public class InitialGameFrame extends JFrame {
    private JFrame thisFrame;
    private int maxX;
    private int maxY;
    private BufferedImage backButtonPic = null;
    private String name = "";
    public InitialGameFrame(){
        super("Start Game");
        this.thisFrame = this;

        try{
            backButtonPic = ImageIO.read(new File("assets/backbutton.png"));
        } catch (IOException e){
        }

        //configure the window
        maxY = Toolkit.getDefaultToolkit().getScreenSize().height-50; //-50 so that the bottom appbar doesn't get in the way
        maxX = (int)Math.round(1.33*maxY);
        this.setSize(maxX, maxY);

        this.setLocationRelativeTo(null); //start the frame in the center of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable (false);

        JPanel mainPanel = new InitialPanel();

        JButton backButton = new JButton(new ImageIcon(backButtonPic));
        backButton.addActionListener(new BackButtonListener());
        backButton.setMaximumSize(new Dimension (backButtonPic.getWidth(),backButtonPic.getHeight()));
        backButton.setContentAreaFilled(false);
        backButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JTextField nameTextField = new JTextField();
        nameTextField.setSize(60, 15);
        JPanel panel = new JPanel();
        panel.add(nameTextField);


        MyKeyListener keyListener = new MyKeyListener();
        this.addKeyListener(keyListener);

        //mainPanel.add(panel);

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
        //Add all panels to the mainPanel according to border layout
        mainPanel.add(Box.createRigidArea(new Dimension(0,15)));
        mainPanel.add(backButton);

        this.add(mainPanel);

        //Start the app
        this.setVisible(true);

    }

    /*private BufferedImage opacityChanger(BufferedImage original, float opacity){
        BufferedImage newImage = new BufferedImage(original.getWidth(), original.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.DST_IN, opacity);
        g.setComposite(ac);
        g.drawImage(newImage, 0,0,null);

        return newImage;

    }*/

    private class InitialPanel extends JPanel {
        private Image background = Toolkit.getDefaultToolkit().getImage("assets/background3.png");
        private Image title = Toolkit.getDefaultToolkit().getImage("assets/title1.png");

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(background, 0, 0, maxX, maxY, this);
            g.drawImage(title, maxX/2-116,20, 232, 50, this);
           // g.drawImage(opacityChanger(blackStripe,0.1f), 0, 850, maxX, 30, this);
            g.drawString(name, 200, 600);
        }
    }

    //This is an inner class that is used to detect a button press
    class TextListener implements ActionListener {  //this is the required class definition
        public void actionPerformed(ActionEvent event)  {

        }
    }

    class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event)  {
            thisFrame.dispose();
            new StartingFrame();
        }

    }

    private class MyKeyListener implements KeyListener {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (!(KeyEvent.getKeyText(e.getKeyCode()).equals("Backspace")) && !(KeyEvent.getKeyText(e.getKeyCode()).equals("Space"))) {
                name = name+KeyEvent.getKeyText(e.getKeyCode());
            } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("Backspace")){
                name = name.substring(0,name.length()-1);
            } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("Space")){
                name = name+" ";
            }
        }

        public void keyReleased(KeyEvent e) {
        }
    }

    public static void main(String[] args){
        new InitialGameFrame();
    }
}
