import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WinnerFrame extends JFrame {

    private static final Image background = Toolkit.getDefaultToolkit().getImage("assets/background3.png");
    JFrame thisFrame;
    WinnerPanel mainPanel;
    private String name;
    private int maxX;
    private int maxY;
    private BufferedImage confirmButtonPic = null;

    WinnerFrame(String name){
        super("Game Over!");
        this.name = name;
        //configure the window
        this.thisFrame = this;
        maxY = 400; //-50 so that the bottom appbar doesn't get in the way
        maxX = 600;
        this.setSize(maxX, maxY);

        this.setLocationRelativeTo(null); //start the frame in the center of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable (false);
        this.setUndecorated(true);

        try{
            confirmButtonPic = ImageIO.read(new File("assets/BlueConfirmButton.png"));
        } catch (IOException e){
        }

        mainPanel = new WinnerPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
        mainPanel.add(Box.createRigidArea(new Dimension(0,15)));


        this.add(mainPanel);
        this.setVisible(true);

    }

    public class WinnerPanel extends JPanel {

        WinnerPanel() {
            this.setSize(new Dimension(maxX, maxY));
            try {
                confirmButtonPic = ImageIO.read(new File("assets/BlueConfirmButton.png"));
            } catch (IOException e) {
            }

            JButton confirmButton = new JButton(new ImageIcon(confirmButtonPic));
            confirmButton.addActionListener(new ConfirmButtonListener());
            confirmButton.setMaximumSize(new Dimension(confirmButtonPic.getWidth(), confirmButtonPic.getHeight()));
            confirmButton.setContentAreaFilled(false);
            confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            confirmButton.setFocusable(false);

            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            this.add(Box.createRigidArea(new Dimension(0, 180)));
            this.add(confirmButton);

            this.setVisible(true);

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Font font1 = Utilities.getFont("assets/AdequateLight.ttf", 30f);
            FontMetrics fontMetrics = g.getFontMetrics(font1);
            g.setFont(font1);
            g.drawImage(background, 0, 0, maxX, maxY, this);
            g.drawString("Game Over! " + name + ", you win!", (maxX - fontMetrics.stringWidth("Game Over! " + name + ", you win!")) / 2, maxY/2-60);
        }
    }

    private class ConfirmButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            thisFrame.dispose();
            new StartingFrame();
        }

    }

    public static void main(String[] args){
        new WinnerFrame("brian");
    }
}
