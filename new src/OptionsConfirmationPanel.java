import sun.net.dns.ResolverConfiguration;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsConfirmationPanel extends JPanel {
    int maxX;
    int maxY;
    //public boolean display;
    JPanel thisPanel;
    Player player;

    public OptionsConfirmationPanel(int maxY, int maxX, Player player) {
        thisPanel = this;
        this.setSize(new Dimension(maxX, maxY));
        this.maxX = maxX;
        this.maxY = maxY;
        this.player = player;

        Color lightGrey = new Color(203, 203, 203);
        JButton cancelButton = new RoundedRectButton(110, 30, lightGrey, "CANCEL");
        cancelButton.addActionListener(new CancelButtonListener());
        Color blue = new Color(0, 122, 203);
        JButton confirmButton = new RoundedRectButton(110, 30, blue, "CONFIRM");
        confirmButton.addActionListener(new ConfirmButtonListener());

        this.setLayout(null);
        confirmButton.setBounds(maxX - 765, maxY - 545,cancelButton.getWidth(), cancelButton.getHeight());
        cancelButton.setBounds(maxX - 880, maxY - 545, confirmButton.getWidth(),confirmButton.getHeight());
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
        g.drawString(OptionsPanel.getAllUnits()[OptionsPanel.getPrevClick()], x + 10, y + 25);
        g.setFont(textFont);
        FontMetrics fontMetrics = g.getFontMetrics(textFont);

        g.drawString(Integer.toString(OptionsPanel.getCosts()[OptionsPanel.getPrevClick()]), x + width - fontMetrics.stringWidth(Integer.toString(OptionsPanel.getCosts()[OptionsPanel.getPrevClick()])) - 10, y + 20);
        g.drawImage(star, x + width - 35 - fontMetrics.stringWidth(Integer.toString(OptionsPanel.getCosts()[OptionsPanel.getPrevClick()])), y + 5, 20, 20, this);
    }

    private class CancelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            GameFrame.setDisplayConfirmation(false);
        }
    }

    private class ConfirmButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
           GameMapPanel.setTrainUnit(true);
           GameMapPanel.setTrainUnitType(OptionsPanel.getAllUnits()[OptionsPanel.getPrevClick()]);
           GameFrame.setDisplayConfirmation(false);
        }
    }
}
