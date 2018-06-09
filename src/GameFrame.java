import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class GameFrame extends JFrame {
    GameMapPanel mapPanel;
    JPanel sidePanel;
    JPanel mainPanel;
    JPanel bottomPanel;
    JPanel bottomSidePanel;
    OptionsPanel optionsPanel;
    JFrame frame;
    static int maxX;
    int maxY;
    int mapPanelLength;
    int optionsPanHeight = 70;
    int optionsPanLength;
    static boolean displayOptions = false;

    GameMapPanel gameMapPanel2;
    GameFrame(Map map) {
        super("Polytopia");
        this.frame = this;

        maxY = Toolkit.getDefaultToolkit().getScreenSize().height - 50; //-50 so that the bottom appbar doesn't get in the way
        maxX = (int) Math.round(1.33 * maxY);
        this.setSize(maxX, maxY);

        this.setLocationRelativeTo(null); //start the frame in the center of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        mapPanelLength = maxY - optionsPanHeight - 10;

        mapPanel = new GameMapPanel(map, mapPanelLength);
        mainPanel = new JPanel();
        sidePanel = new JPanel();
        bottomPanel = new JPanel();
        optionsPanel = new OptionsPanel(optionsPanHeight, 700);
        bottomSidePanel = new JPanel();

        optionsPanLength = optionsPanel.getWidth();
        sidePanel.setPreferredSize(new Dimension(((maxX-mapPanelLength)/2) , mapPanelLength));
        bottomSidePanel.setPreferredSize(new Dimension(((maxX-optionsPanLength)/2), optionsPanHeight));

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(optionsPanel, BorderLayout.CENTER);
        //bottomPanel.add(optionsPanel);
        bottomPanel.add(bottomSidePanel, BorderLayout.WEST);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(mapPanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.WEST);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);
        bottomPanel.setVisible(false);

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
            if (displayOptions){
                bottomPanel.setVisible(true);
            } else {
                bottomPanel.setVisible(false);
            }
        }
    }

    public static void main(String[] args){
        Map map = new Map(20);
        new GameFrame(map);
    }
}