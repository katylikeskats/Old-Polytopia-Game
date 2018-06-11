import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class TechOptionsPanel extends JPanel {
    int maxX;
    int maxY;
    public static boolean display;
    JPanel thisPanel;

    TechOptionsPanel(int maxY, int maxX) {
        thisPanel = this;
        this.setMaximumSize(new Dimension(maxX, maxY));
        //this.setVisible(false);
        this.maxX = maxX;
        this.maxY = maxY;
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 30));
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
        this.setVisible(false);
        Thread t = new Thread(new Runnable() {
            public void run() {
                animate();
            }
        }); //start the gameLoop
        t.start();
    }

    public void animate() {
        while (true) {
            this.repaint();
            if (display) {
                this.setVisible(true);
            } else {
                this.setVisible(false);
            }
        }
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
        // g.drawString(TechTreeFrame.allTech[TechTreeFrame.prevClick], x + 10, y + 25);
        g.setFont(textFont);
        FontMetrics fontMetrics = g.getFontMetrics(textFont);
        // g.drawString(Integer.toString(TechTreeFrame.prevCost), x + width - fontMetrics.stringWidth(Integer.toString(TechTreeFrame.prevCost)) - 10, y + 20);
        //g.drawImage(star, x+width - 35 - fontMetrics.stringWidth(Integer.toString(TechTreeFrame.prevCost)), y+5, 20,20, this);
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
            thisPanel.setVisible(false);
        }
    }

    private class ConfirmButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.out.println("clicked");
        }
    }
}
    /*public static void main(String[] args){
        JFrame frame = new JFrame();
        int maxY = Toolkit.getDefaultToolkit().getScreenSize().height - 50; //-50 so that the bottom appbar doesn't get in the way
        int maxX = (int) Math.round(1.33 * maxY);
        frame.setSize(maxX, maxY);

        frame.setUndecorated(true);
        frame.setBackground(new Color(0,0,0,0));
        frame.setLocationRelativeTo(null); //start the frame in the center of the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.add(new TechOptionsPanel(maxY, maxX));
        frame.requestFocusInWindow();
        frame.setVisible(true);

    }
}*/

