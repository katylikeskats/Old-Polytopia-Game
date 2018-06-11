import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.text.html.Option;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JLayeredPane;

public class TechTreeFrame extends JFrame {
    JFrame thisFrame;
    TechOptionsPanel optionsPanel;
    BufferedImage backButtonPic = null;
    String[] allTech = {"Archery", "Forestry", "Hunting", "Farming", "Shields", "Organization", "Philosophy", "Smithery", "Climbing", "Ninja", "Chivalry", "Riding", "Whaling", "Sailing", "Fishing"};
    String[] startingTech = {"Hunting", "Climbing", "Organization", "Riding", "Fishing"};
    int[][] techCoords;

    Player player;
    boolean displayOptions = false;
    int maxX;
    int maxY;
    int prevClick;
    int prevCost;
    int tierOneCost;
    int tierTwoCost;
    ArrayList<String> obtainedTech;
    TechTreeFrame(Player player){
        super("TechTree");
        this.thisFrame = this;
        maxY = Toolkit.getDefaultToolkit().getScreenSize().height - 50; //-50 so that the bottom appbar doesn't get in the way
        maxX = (int) Math.round(1.33 * maxY);
        this.setSize(maxX, maxY);

        this.setLocationRelativeTo(null); //start the frame in the center of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setUndecorated(true);

        this.player = player;
        this.tierOneCost = player.getTierOneCost();
        this.tierTwoCost = player.getTierTwoCost();
        this.obtainedTech = player.getTechnology();

        JPanel blackBackground = new JPanel();
        blackBackground.setSize(new Dimension(maxX, maxY));
        blackBackground.setBackground(new Color(1,1,1));
        blackBackground.setLocation(0,0);
        TechnologyPanel mainPanel = new TechnologyPanel(maxY, maxX, player.getTechnology(), player.getTierOneCost(), player.getTierTwoCost(), player.getCurrency());
        //this.add(mainPanel);

       try{
            backButtonPic = ImageIO.read(new File("assets/XButton.png"));
        } catch (IOException e){
        }
        JButton backButton = new RoundButton(35, new ImageIcon(backButtonPic));
        backButton.addActionListener(new BackButtonListener());
        backButton.setFocusable(false);
        backButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
        //Add all panels to the mainPanel according to border layout
        mainPanel.add(Box.createRigidArea(new Dimension(0,15)));
        mainPanel.add(backButton);

        optionsPanel = new TechOptionsPanel(maxY, maxX);
        //mainPanel.add(optionsPanel);

        JLayeredPane lPane = new JLayeredPane();
        lPane.setPreferredSize(new Dimension(maxX, maxY));
        mainPanel.setSize(new Dimension(maxX, maxY));
        mainPanel.setLocation(0,0);
        //mainPanel.setVisible(false);
        mainPanel.setOpaque(false);
        lPane.add(blackBackground, new Integer(0));
        lPane.add(mainPanel, new Integer(1));
        optionsPanel.setSize(new Dimension(maxX, maxY));
        optionsPanel.setLocation(0,0);
        optionsPanel.setVisible(false);
        optionsPanel.setOpaque(false);
        lPane.add(optionsPanel, new Integer(2));
        lPane.setOpaque(false);
        this.add(lPane, BorderLayout.CENTER);

        MyMouseListener mouseListener = new MyMouseListener();
        this.addMouseListener(mouseListener);

        techCoords = TechnologyPanel.getTechCoords();

        //Start the game loop in a separate thread
        Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop
        t.start();

        this.setVisible(true);
    }

    public void animate(){
        while(true){
            this.repaint();
            if (displayOptions){
                optionsPanel.setVisible(true);
            } else {
                optionsPanel.setVisible(false);
            }
        }
    }

    class BackButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event)  {
            thisFrame.dispose();
        }

    }

    public class TechOptionsPanel extends JPanel {
        int maxX;
        int maxY;
        //public boolean display;
        JPanel thisPanel;

        TechOptionsPanel(int maxY, int maxX) {
            thisPanel = this;
            this.setMaximumSize(new Dimension(maxX, maxY));
            //this.setVisible(false);
            this.maxX = maxX;
            this.maxY = maxY;
            Color lightGrey = new Color(203, 203, 203);
            JButton cancelButton = new RoundedRectButton(110, 30, lightGrey, "CANCEL");
            cancelButton.addActionListener(new CancelButtonListener());
            Color blue = new Color(0, 122, 203);
            JButton confirmButton = new RoundedRectButton(110, 30, blue, "RESEARCH");
            confirmButton.addActionListener(new ConfirmButtonListener());

            this.setLayout(null);
            confirmButton.setBounds(maxX - 475, maxY - 330, ((RoundedRectButton) cancelButton).width, ((RoundedRectButton) cancelButton).height);
            cancelButton.setBounds(maxX - 590, maxY - 330, ((RoundedRectButton) confirmButton).width, ((RoundedRectButton) confirmButton).height);
            this.add(confirmButton);
            this.add(cancelButton);
            this.setOpaque(false);
            this.setVisible(false);

        }


        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image star = Toolkit.getDefaultToolkit().getImage("assets/Star2.png");
            int width = 240;
            int height = 135;
            int x = (maxX - width) / 2;
            int y = (maxY - height) / 2;
                g.setColor(Color.black);

                g.fillRoundRect(x, y, width, height, 20, 20);
                g.setColor(Color.white);
                Font titleFont = Utilities.getFont("assets/AdequateLight.ttf", 18f);
                Font textFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
                g.setFont(titleFont);
                g.drawString(allTech[prevClick], x + 10, y + 25);
                g.setFont(textFont);
                FontMetrics fontMetrics = g.getFontMetrics(textFont);

             g.drawString(Integer.toString(prevCost), x + width - fontMetrics.stringWidth(Integer.toString(prevCost)) - 10, y + 20);
            g.drawImage(star, x+width - 35 - fontMetrics.stringWidth(Integer.toString(prevCost)), y+5, 20,20, this);
        }

        private class RoundedRectButton extends JButton {
            int width;
            int height;
            Color color;
            String name;

            public RoundedRectButton(int width, int height, Color color, String name) {
                super();
                this.width = width;
                this.height = height;
                this.color = color;
                this.name = name;
                Dimension size = new Dimension(width, height);
                setPreferredSize(size);
                setBorderPainted(false);
                setContentAreaFilled(false);
            }

            public void paintComponent(Graphics g) {
                g.setColor(color);
                g.fillRoundRect(0, 0, width, height, 20, 20);
                g.setColor(Color.white);
                drawCenteredString(g, width, 0, height - 10, name);
            }
        }

        public void drawCenteredString(Graphics g, int rightX, int leftX, int y, String string) {
            Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
            FontMetrics fontMetrics = g.getFontMetrics(font);
            g.setFont(font);
            g.drawString(string, (rightX - leftX - fontMetrics.stringWidth(string)) / 2 + leftX, y);

        }

        private class CancelButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                displayOptions = false;
            }
        }

        private class ConfirmButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                if (player.getCurrency() >= prevCost){
                    player.setCurrency(player.getCurrency()-prevCost);
                    player.addTechnology(allTech[prevClick]);
                    displayOptions = false;
                    thisFrame.dispose();
                    new TechTreeFrame(player);
                }
            }
        }
    }

    public static void main(String[] args){
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

    }

    private class MyMouseListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            for (int i = 0; i < techCoords.length ; i++){
                if ((Math.pow((e.getX() - techCoords[i][0]), 2)+Math.pow(e.getY() - techCoords[i][1],2)) <= Math.pow(((TechnologyPanel.getDiameter()/2)), 2)) {
                    if (!Utilities.contains(obtainedTech, allTech[i])){
                        if (!displayOptions) {
                            displayOptions = true;
                            prevClick = i;
                            if (Utilities.contains(startingTech, allTech[i])){
                                prevCost = tierOneCost;
                            } else {
                                prevCost = tierTwoCost;
                            }
                        } else if ((displayOptions) && (prevClick != i)) {
                            displayOptions = true;
                            prevClick = i;
                        } else if ((displayOptions) && (prevClick == i)) {
                            displayOptions = false;
                        }
                    }
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

    } //end of mouselistener
}
