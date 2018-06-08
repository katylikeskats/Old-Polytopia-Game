import com.sun.deploy.panel.JavaPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class GameFrame extends JFrame {
    GameMapPanel mapPanel;
    JPanel sidePanel;
    JPanel mainPanel;
    JFrame frame;
    int maxX;
    int maxY;
    int mapPanelLength;

    GameFrame(Map map) {
        super("Polytopia");
        this.frame = this;

        maxY = Toolkit.getDefaultToolkit().getScreenSize().height - 50; //-50 so that the bottom appbar doesn't get in the way
        maxX = (int) Math.round(1.33 * maxY);
        this.setSize(maxX, maxY);

        this.setLocationRelativeTo(null); //start the frame in the center of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        mapPanelLength = maxY;
        mapPanel = new GameMapPanel(map, mapPanelLength);
        mainPanel = new JPanel();
        sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(((maxX-mapPanelLength)/2) , maxY-80));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(mapPanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.LINE_START);

        //mapPanel.setVisible(true);
        this.add(mainPanel);

        //Start the game loop in a separate thread
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop
        t.start();

        this.setVisible(true);
    }

    public void animate(){
        while(true){
            this.repaint();
        }
    }

    public static void main(String[] args){
        Map map = new Map(20);
        new GameFrame(map);
    }
}