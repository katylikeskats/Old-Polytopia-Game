import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;

public class GameFrame extends JFrame {
    GameMapPanel mapPanel;
    JPanel sidePanel;
    JPanel mainPanel;
    JPanel bottomPanel;
    JPanel bottomSidePanel;
    MenuPanel menuPanel;
    OptionsPanel optionsPanel;
    JFrame frame;
    static int maxX;
    int maxY;
    int mapPanelLength;
    int optionsPanHeight = 70;
    int optionsPanLength;
    int menuPanelWidth = 60;
    static boolean turnEnd = false;
    static boolean displayOptions;

    GameMapPanel gameMapPanel2;
    GameFrame(Map map, boolean[][] mask, Player player) {
        super("Polytopia");
        this.frame = this;

        maxY = Toolkit.getDefaultToolkit().getScreenSize().height - 50; //-50 so that the bottom appbar doesn't get in the way
        maxX = (int) Math.round(1.33 * maxY);
        this.setSize(maxX, maxY);

        this.setLocationRelativeTo(null); //start the frame in the center of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        mapPanelLength = maxY - optionsPanHeight - 10;

        mapPanel = new GameMapPanel(map, mapPanelLength, mask, player);
        mainPanel = new JPanel();
        sidePanel = new JPanel();
        bottomPanel = new JPanel();
        menuPanel = new MenuPanel(mapPanelLength, menuPanelWidth,player);
        optionsPanel = new OptionsPanel(optionsPanHeight, 700);
        bottomSidePanel = new JPanel();


        sidePanel.setBackground(Color.black);
        bottomPanel.setBackground(Color.black);
        mapPanel.setBackground(Color.black);
        menuPanel.setBackground(Color.black);

        optionsPanLength = optionsPanel.getWidth();
        menuPanel.setPreferredSize(new Dimension(menuPanelWidth, mapPanelLength));
        sidePanel.setPreferredSize(new Dimension(maxX-mapPanelLength-menuPanelWidth, mapPanelLength));
        bottomSidePanel.setPreferredSize(new Dimension(((maxX-optionsPanLength)/2), optionsPanHeight));

        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(optionsPanel, BorderLayout.CENTER);
        bottomPanel.add(bottomSidePanel, BorderLayout.WEST);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(mapPanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.WEST);
        mainPanel.add(menuPanel, BorderLayout.EAST);
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
            if (turnEnd){
                frame.dispose();
            }
        }
    }

    /*
    public static void main(String[] args){
        Map map = new Map(20, 4); //Test run
        new GameFrame(map);
    }
    */
}