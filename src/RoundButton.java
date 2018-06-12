import java.awt.*;
import java.awt.geom.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class RoundButton extends JButton {
    ImageIcon image;

    public RoundButton(int radius, ImageIcon image) {
        super(image);
        Dimension size = new Dimension(radius, radius);
        setFocusable(false);
        setPreferredSize(size);
        setBorderPainted(false);
        setContentAreaFilled(false);
    }
}
