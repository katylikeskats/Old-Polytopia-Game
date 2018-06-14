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

        RoundButton techButton = new RoundButton(90, new ImageIcon(techPic));
        techButton.addActionListener(new TechButtonListener());
        RoundButton nextTurnButton = new RoundButton(90, new ImageIcon(nextTurnPic));
        nextTurnButton.addActionListener(new NextTurnListener());

        techButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        nextTurnButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,30)));
        this.add(Box.createRigidArea(new Dimension(8, 0)));
        this.add(techButton);

        this.add(Box.createRigidArea(new Dimension(0,10)));
        this.add(nextTurnButton);
    }

    private class NextTurnListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            player.setTurnEnd(true);
            GameFrame.setTurnEnd(true);
        }
    }

    private class TechButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            new TechTreeFrame(player);
        }
    }

}
