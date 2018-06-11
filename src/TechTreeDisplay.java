import javax.swing.JLayeredPane;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;
public class TechTreeDisplay {
    TechTreeDisplay(Player player){
        TechTreeFrame frame = new TechTreeFrame(player);
        /**JFrame frame = new JFrame();
        int maxY = Toolkit.getDefaultToolkit().getScreenSize().height - 50; //-50 so that the bottom appbar doesn't get in the way
        int maxX = (int) Math.round(1.33 * maxY);
        frame.setSize(maxX, maxY);
        frame.setLocationRelativeTo(null); //start the frame in the center of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(true);
         */
        TechnologyPanel mainPanel = new TechnologyPanel(frame.getSize().height, frame.getSize().width, player.getTechnology(), player.getTierOneCost(), player.getTierTwoCost(), player.getCurrency());
        TechOptionsPanel optionsPanel = new TechOptionsPanel(frame.getSize().height, frame.getSize().width);
        JLayeredPane lPane = new JLayeredPane();
        frame.add(mainPanel);
        //lPane.add(optionsPanel, new Integer(0));
        optionsPanel.setVisible(false);
        //frame.add(lPane);
        frame.setVisible(true);
    }

    public static void main(String[] args){
            ArrayList<String> tech = new ArrayList<>();
            tech.add("Riding");
            tech.add("Climbing");
            tech.add("Fishing");
            tech.add("Whaling");
            tech.add("Ninja");
            tech.add("Hunting");
            tech.add("Organization");
            tech.add("StartingTech");

            /*Player player = new Player();
            player.setTechnology(tech);
            player.setCurrency(8);
            player.setTierOneCost(5);
            player.setTierTwoCost(7);
            new TechTreeDisplay(player);
            */
    }
}
