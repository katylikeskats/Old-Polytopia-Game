//Imports
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
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class StartingFrame extends JFrame {
    public static int maxX;
    public static int maxY;
    private BufferedImage startButtonPic = null;
    private BufferedImage instructionButtonPic = null;
    private JFrame thisFrame;

        //Constructor - this runs first
        public StartingFrame() {
            super("Polytopia");
            this.thisFrame = this;
            try{
                startButtonPic = ImageIO.read(new File("assets/startbutton.png"));
                instructionButtonPic = ImageIO.read(new File("assets/instructionbutton.png"));
            } catch (IOException e){

            }


            //configure the window
            maxY = Toolkit.getDefaultToolkit().getScreenSize().height-50; //-50 so that the bottom appbar doesn't get in the way
            maxX = (int)Math.round(1.33*maxY);
            this.setSize(maxX, maxY);

            this.setLocationRelativeTo(null); //start the frame in the center of the screen
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable (false);

            JPanel mainPanel = new StartingMenuPanel();

            JButton startButton = new JButton(new ImageIcon(startButtonPic));
            startButton.addActionListener(new StartButtonListener());
            startButton.setMaximumSize(new Dimension (startButtonPic.getWidth(),startButtonPic.getHeight()));
            startButton.setContentAreaFilled(false);
            startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton instructionButton = new JButton(new ImageIcon(instructionButtonPic));
            instructionButton.addActionListener(new InstructionButtonListener());
            instructionButton.setMaximumSize(new Dimension(instructionButtonPic.getWidth(), instructionButtonPic.getHeight()));
            instructionButton.setContentAreaFilled(false);
            instructionButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
            //Add all panels to the mainPanel according to border layout
            mainPanel.add(Box.createRigidArea(new Dimension(0,(int) Math.round(maxY*0.31))));
            mainPanel.add(startButton);
            mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
            //mainPanel.add(instructionButton);

            this.add(mainPanel);

            //Start the app
            this.setVisible(true);


        }


        class StartingMenuPanel extends JPanel {
            private Image background = Toolkit.getDefaultToolkit().getImage("assets/background.png");

            public void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(background, 0, 0, maxX, maxY,this);
            }
        }
        //This is an inner class that is used to detect a button press

    class StartButtonListener implements ActionListener {  //this is the required class definition
            public void actionPerformed(ActionEvent event)  {
                thisFrame.dispose();
                new InitialGameFrame();
            }

        }


    //This is an inner class that is used to detect a button press
    class InstructionButtonListener implements ActionListener {  //this is the required class definition
            public void actionPerformed(ActionEvent event)  {
            thisFrame.dispose();
            new InstructionFrame();
            }

        }

        //Main method starts this application
        public static void main(String[] args) {
            new StartingFrame();
        }

}



