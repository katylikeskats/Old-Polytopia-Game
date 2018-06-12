import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MenuPanel extends JPanel {
    BufferedImage techPic = null;
    BufferedImage nextTurnPic = null;
    Player player;
    MenuPanel(int height, int width, Player player){
        this.setSize(width, height);
        this.player = player;
        this.setBackground(Color.black);
        try{
            techPic = ImageIO.read(new File("assets/TechButton.png"));
            nextTurnPic = ImageIO.read(new File("assets/NextTurnButton.png"));
        } catch (IOException e){
        }

        RoundButton techButton = new RoundButton(20, new ImageIcon(techPic));
        techButton.addActionListener(new TechButtonListener());
        RoundButton nextTurnButton = new RoundButton(20, new ImageIcon(nextTurnPic));
        nextTurnButton.addActionListener(new NextTurnListener());

        techButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        nextTurnButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,30)));
        this.add(techButton);

        this.add(Box.createRigidArea(new Dimension(0,5)));
        this.add(nextTurnButton);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.white);
        g.drawString("Player", 2, 20);
    }

    private class NextTurnListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            player.setTurnEnd(true);
            GameFrame.turnEnd = true;
        }
    }

    private class TechButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            new TechTreeFrame(player);
        }
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        int maxY = Toolkit.getDefaultToolkit().getScreenSize().height-50; //-50 so that the bottom appbar doesn't get in the way
        int maxX = (int)Math.round(1.33*maxY);
        frame.setSize(maxX, maxY);

        frame.setLocationRelativeTo(null); //start the frame in the center of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable (false);

        ArrayList<String> tech = new ArrayList<>();
        tech.add("Riding");
        tech.add("Climbing");
        tech.add("Fishing");
        tech.add("Whaling");
        tech.add("StartingTech");
        Player player = new Player();
            player.setTechnology(tech);
            player.setCurrency(24);
            player.setTierOneCost(5);
            player.setTierTwoCost(7);
            new TechTreeFrame(player);
        frame.add(new MenuPanel(maxY, maxX, player));
        frame.setVisible(true);
    }
}
