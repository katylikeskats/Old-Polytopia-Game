import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TechTreeFrame extends JFrame {
    JFrame thisFrame;
    BufferedImage backButtonPic = null;
    TechTreeFrame(Player player){
        super("TechTree");
        this.thisFrame = this;
        int maxY = Toolkit.getDefaultToolkit().getScreenSize().height - 50; //-50 so that the bottom appbar doesn't get in the way
        int maxX = (int) Math.round(1.33 * maxY);
        this.setSize(maxX, maxY);

        this.setLocationRelativeTo(null); //start the frame in the center of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        TechnologyPanel mainPanel = new TechnologyPanel(maxY, maxX, player.getTechnology(), player.getTierOneCost(), player.getTierTwoCost(), player.getCurrency());
        this.add(mainPanel);

       try{
            backButtonPic = ImageIO.read(new File("assets/XButton.png"));
        } catch (IOException e){
        }
        JButton backButton = new RoundButton(35, new ImageIcon(backButtonPic));
        backButton.addActionListener(new BackButtonListener());
        backButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
        //Add all panels to the mainPanel according to border layout
        mainPanel.add(Box.createRigidArea(new Dimension(0,15)));

        mainPanel.add(backButton);

        this.setVisible(true);
    }

    class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event)  {
            thisFrame.dispose();
        }

    }

    public static void main(String[] args){
            ArrayList<String> tech = new ArrayList<>();
            tech.add("Riding");
            tech.add("Climbing");
            tech.add("Fishing");
            tech.add("Whaling");
            tech.add("Ninja");
            tech.add("StartingTech");

            Player player = new Player();
            player.setTechnology(tech);
            player.setCurrency(8);
            player.setTierOneCost(5);
            player.setTierTwoCost(7);
            new TechTreeFrame(player);

    }
}
