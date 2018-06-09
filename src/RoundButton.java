import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class RoundButton extends JButton {
    ImageIcon image;
    public RoundButton(int height, int width, String label) {
        super(label);
        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setContentAreaFilled(false);
    }

    // Paint the round background and label.
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillOval(0, 0, getSize().width-1, getSize().height-1);
// This call will paint the label and the
        // focus rectangle
    }

    // Paint the border of the button using a simple stroke.
    protected void paintBorder(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, getSize().width-1, getSize().height-1);

    }

    public static void main(String[] args){
        StartingFrame frame = new StartingFrame();
    }
}
