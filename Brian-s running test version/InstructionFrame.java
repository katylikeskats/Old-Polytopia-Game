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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InstructionFrame extends JFrame {
    private JFrame thisFrame;
    private int maxX;
    private int maxY;
    private BufferedImage backButtonPic = null;
    public InstructionFrame(){
        super("Instructions");
        this.thisFrame = this;

        try{
            backButtonPic = ImageIO.read(new File("backbutton.png"));
        } catch (IOException e){
        }

        //configure the window
        maxY = Toolkit.getDefaultToolkit().getScreenSize().height-50; //-50 so that the bottom appbar doesn't get in the way
        maxX = (int)Math.round(1.33*maxY);
        this.setSize(maxX, maxY);

        this.setLocationRelativeTo(null); //start the frame in the center of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable (false);

        JPanel mainPanel = new InstructionPanel();

        JButton backButton = new JButton(new ImageIcon(backButtonPic));
        backButton.addActionListener(new BackButtonListener());
        backButton.setMaximumSize(new Dimension (backButtonPic.getWidth(),backButtonPic.getHeight()));
        backButton.setContentAreaFilled(false);
        backButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
        //Add all panels to the mainPanel according to border layout
        mainPanel.add(Box.createRigidArea(new Dimension(0,15)));
        mainPanel.add(backButton);

        this.add(mainPanel);

        //Start the app
        this.setVisible(true);

    }

    class InstructionPanel extends JPanel {
        private Image background = Toolkit.getDefaultToolkit().getImage("background2.png");

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(background, 0, 0, maxX, maxY, this);
        }
    }

    class BackButtonListener implements ActionListener {  //this is the required class definition
        public void actionPerformed(ActionEvent event)  {
            thisFrame.dispose();
            new StartingFrame();
        }

    }

}


