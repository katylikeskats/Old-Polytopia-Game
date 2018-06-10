import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Component;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.FontMetrics;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class TechnologyPanel extends JPanel {
    int height;
    int width;
    ArrayList<String> tech;
    int tierOneCost;
    int tierTwoCost;
    int stars;
    BufferedImage backButtonPic = null;
    Image star = Toolkit.getDefaultToolkit().getImage("assets/Star2.png");

    TechnologyPanel(int height, int width, ArrayList<String> tech, int tierOneCost, int tierTwoCost, int stars){
        this.height = height;
        this.width = width;
        this.tech = tech;
        this.tierOneCost = tierOneCost;
        this.tierTwoCost = tierTwoCost;
        this.stars = stars;
        this.setBackground(new Color(1,1,1));

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setDoubleBuffered(true);
        Image pic = Toolkit.getDefaultToolkit().getImage("assets/BardurHead.png");

        Image riding = Toolkit.getDefaultToolkit().getImage("RidingTech.png");
        Image ninja = Toolkit.getDefaultToolkit().getImage("NinjaTech.png");
        Image chivalry = Toolkit.getDefaultToolkit().getImage("ChivalryTech.png");

        Image organization = Toolkit.getDefaultToolkit().getImage("OrganizationTech.png");
        Image shields = Toolkit.getDefaultToolkit().getImage("ShiedsTech.png");
        Image farming = Toolkit.getDefaultToolkit().getImage("FarmingTech.png");

        Image climbing = Toolkit.getDefaultToolkit().getImage("ClingTech.png");
        Image smithery = Toolkit.getDefaultToolkit().getImage("SmitheryTech.png");
        Image philosophy = Toolkit.getDefaultToolkit().getImage("PhilosophyTech.png");

        Image fishing = Toolkit.getDefaultToolkit().getImage("FishingTech.png");
        Image whaling = Toolkit.getDefaultToolkit().getImage("WhalingTech.png");
        Image sailing = Toolkit.getDefaultToolkit().getImage("SailingTech.png");

        Image hunting = Toolkit.getDefaultToolkit().getImage("HuntingTech.png");
        Image forestry = Toolkit.getDefaultToolkit().getImage("ForestryTech.png");
        Image archery = Toolkit.getDefaultToolkit().getImage("ArcheryTech.png");

        Color black = new Color(1,1,1);
        Color grey = new Color(100, 100, 100);
        int refX;
        int refY;
        int midX = width/2;
        int midY = height/2 + 20;
        int length = (int) Math.round(height*0.12);

        //technology bubbles
        int lineLength = length+30;
        int changeX;
        int changeY;

        //top vertical branch
        refX = midX;
        refY = midY - lineLength;
        decideToDraw(g, "Archery", "Hunting", archery, refX, refY, (int) Math.round(lineLength*(Math.cos((0.33)*Math.PI))), (int) Math.round(lineLength*(Math.sin((0.33)*Math.PI))), length);
        decideToDraw(g, "Forestry", "Hunting", forestry, refX, refY, -(int) Math.round(lineLength * (Math.cos((0.33) * Math.PI))), (int) Math.round(lineLength * (Math.sin((0.33) * Math.PI))), length);
        decideToDraw(g, "Hunting", "StartingTech", hunting, midX, midY, 0, lineLength, length);
        //bottom right branch
        refX = midX + (int) Math.round(lineLength*(Math.cos(0.3*Math.PI)));;
        refY = midY + (int) Math.round(lineLength*(Math.sin(0.3*Math.PI)));
        decideToDraw(g, "Farming", "Organization", farming, refX, refY, (int) Math.round(lineLength*(Math.cos((0.5)*Math.PI))), (int) -Math.round(lineLength*(Math.sin((0.5)*Math.PI))), length);
        decideToDraw(g,"Shields", "Organization", shields, refX, refY, (int) -Math.round(lineLength*(Math.cos((0.16)*Math.PI))), (int) -Math.round(lineLength*(Math.sin((0.16)*Math.PI))), length);
        decideToDraw(g, "Organization", "StartingTech", organization, midX, midY, (int) -Math.round(lineLength*(Math.cos(0.3*Math.PI))), (int) -Math.round(lineLength*(Math.sin(0.3*Math.PI))), length);

        //bottom left branch
        refX = midX - (int) Math.round(lineLength*(Math.cos(0.3*Math.PI)));;
        refY = midY + (int) Math.round(lineLength*(Math.sin(0.3*Math.PI)));
        decideToDraw(g, "Philosophy", "Climbing", philosophy, refX, refY, (int) -Math.round(lineLength*(Math.cos((0.5)*Math.PI))), (int) -Math.round(lineLength*(Math.sin((0.5)*Math.PI))), length);
        decideToDraw(g, "Smithery", "Climbing", smithery, refX, refY, (int) Math.round(lineLength*(Math.cos((0.16)*Math.PI))), (int) -Math.round(lineLength*(Math.sin((0.16)*Math.PI))), length);
        decideToDraw(g, "Climbing", "StartingTech", climbing, midX, midY, (int) Math.round(lineLength*(Math.cos(0.3*Math.PI))), (int) -Math.round(lineLength*(Math.sin(0.3*Math.PI))), length);

        //top right branch
        refX = midX + (int) Math.round(lineLength*(Math.cos(0.1*Math.PI)));;
        refY = midY - (int) Math.round(lineLength*(Math.sin(0.1*Math.PI)));
        decideToDraw(g, "Ninja","Riding", ninja, refX, refY, (int) -Math.round(lineLength*(Math.cos((0.33)*Math.PI))), (int) Math.round(lineLength*(Math.sin((0.33)*Math.PI))), length);
        decideToDraw(g, "Chivalry", "Riding", chivalry, refX, refY, (int) -Math.round(lineLength*(Math.cos((0)*Math.PI))), (int) Math.round(lineLength*(Math.sin((0)*Math.PI))), length);
        decideToDraw(g, "Riding", "StartingTech", riding, midX, midY, (int) -Math.round(lineLength*(Math.cos(0.1*Math.PI))), (int) Math.round(lineLength*(Math.sin(0.1*Math.PI))), length);

        //top left branch
        refX = midX - (int) Math.round(lineLength*(Math.cos(0.1*Math.PI)));;
        refY = midY - (int) Math.round(lineLength*(Math.sin(0.1*Math.PI)));
        decideToDraw(g, "Whaling", "Fishing", whaling, refX, refY, (int) Math.round(lineLength*(Math.cos((0.33)*Math.PI))), (int) Math.round(lineLength*(Math.sin((0.33)*Math.PI))), length);
        decideToDraw(g, "Sailing", "Fishing", sailing, refX, refY, (int) Math.round(lineLength*(Math.cos((0)*Math.PI))), (int) Math.round(lineLength*(Math.sin((0)*Math.PI))), length);
        decideToDraw(g, "Fishing", "StartingTech", fishing, midX, midY, (int) Math.round(lineLength*(Math.cos(0.1*Math.PI))), (int) Math.round(lineLength*(Math.sin(0.1*Math.PI))), length);

        g.drawImage(pic, midX - length/2,midY-length/2,length,length, this);


        Font titleFont = getFont("assets/AdequateLight.ttf", 30f);
        Font subtitleFont = new Font(Font.SANS_SERIF, Font.PLAIN, 11);
        Font resourceFont = getFont("assets/AdequateLight.ttf", 15f);
        g.setFont(titleFont);
        g.drawString("Research Center", 5,35);
        g.setFont(subtitleFont);
        g.drawString("Technologies get more expensive", 5, 55);
        g.drawString("the more cities you have.", 5, 67);
        g.setFont(resourceFont);
        g.drawString("Resources", 5, 100);
        g.drawImage(star, 5, 108, 15,15, this);
        g.drawString(Integer.toString(stars), 22, 122);
    }

    /**
     * Takes in a technology, and calls on the appropriate draw method whether it is locked, unlocked or obtained (bought)
     * @param g Graphics to pass into the methods which will draw the images
     * @param technology String which is the technology it is determining how to draw (needs to determine whether it has been unlocked, bought or is still locked)
     * @param previousTech The technology which unlocks the current technology; "StartingTech" if it is one of the tier 1 technologies
     * @param pic The picture of the technology which will go inside the circle
     * @param refX The reference X (from which it is branching)
     * @param refY The reference Y (from which it is branching)
     * @param changeX The change in the refX coordinate (to its actual position)
     * @param changeY The change in the refX coordinate (to its actual position)
     * @param length The length of the line connecting the circle to the previous circle
     */
    public void decideToDraw (Graphics g, String technology, String previousTech, Image pic, int refX, int refY, int changeX, int changeY, int length){
        if (contains(tech, technology)) {
            drawObtainedTech(g,refX, refY, changeX, changeY, length, pic, technology);
        } else if (contains(tech, previousTech)) {
            int lengthStar = (int) Math.round(length*0.18);
            if (previousTech.equals("StartingTech")){
                drawUnlockedTech(g,refX, refY, changeX, changeY, length, pic, technology, tierOneCost);
                drawCost(g,refX - changeX + length/2, refX - changeX - length/2, refY - changeY - length/2 + (int) Math.round(lengthStar*1.5), lengthStar, tierOneCost);
            } else {
                drawUnlockedTech(g,refX, refY, changeX, changeY, length, pic, technology, tierTwoCost);
                drawCost(g,refX - changeX + length/2, refX - changeX - length/2, refY - changeY - length/2 + (int) Math.round(lengthStar*1.5), lengthStar, tierTwoCost);
            }
        } else {
            drawLockedTech(g,refX, refY, changeX, changeY, length);
        }
    }

    public void drawLockedTech(Graphics g, int refX, int refY, int changeX, int changeY, int length){
        Color black = new Color(1,1,1);
        Color grey = new Color(100, 100, 100);
        g.setColor(grey);
        g.drawLine(refX, refY, refX - changeX, refY - changeY);
        g.setColor(black);
        g.fillOval(refX - changeX - length/2+1,refY - changeY - length/2, length, length);
        g.setColor(grey);
        g.drawOval(refX - changeX - length/2, refY - changeY - length/2, length, length);
    }

    public void drawObtainedTech(Graphics g, int refX, int refY, int changeX, int changeY, int length, Image pic, String technology){
        Color green = new Color(18,207,65);
        g.setColor(green);
        g.drawLine(refX, refY, refX - changeX, refY - changeY);
        g.fillOval(refX - changeX - length/2,refY - changeY - length/2, length, length);
        g.drawImage(pic, refX, refY, length, length, this);
        g.setColor(Color.white);
        drawCenteredString(g, refX - changeX + length/2, refX - changeX - length/2, refY - changeY + length/3, technology);
    }

    public void drawUnlockedTech(Graphics g, int refX, int refY, int changeX, int changeY, int length, Image pic, String technology, int cost){
        Color blue = new Color(82,185,255);
        g.setColor(Color.white);
        g.drawLine(refX, refY, refX - changeX, refY - changeY);
        g.setColor(blue);
        g.fillOval(refX - changeX - length/2,refY - changeY - length/2, length, length);
        g.setColor(Color.white);
        drawCenteredString(g, refX - changeX + length/2, refX - changeX - length/2, refY - changeY + length/3, technology);
        if (stars < cost) {
            g.setColor(Color.red);
        }
        g.drawOval(refX - changeX - length/2, refY - changeY - length/2, length, length);
        g.drawImage(pic, refX, refY, length, length, this);
    }

    public void drawCost(Graphics g, int rightX, int leftX, int y, int lengthStar, int price){
        String priceStr = Integer.toString(price);
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
        FontMetrics fontMetrics = g.getFontMetrics(font);
        g.setFont(font);
        int x = (rightX - leftX - (fontMetrics.stringWidth(priceStr)+3+lengthStar))/2 + leftX;
        g.drawImage(star, x, y - lengthStar, lengthStar, lengthStar,this);
        g.drawString(priceStr, x + lengthStar + 3, y);
    }
    public void drawCenteredString(Graphics g, int rightX, int leftX, int y, String string){
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 11);
        FontMetrics fontMetrics = g.getFontMetrics(font);
        g.setFont(font);
        g.drawString(string, (rightX - leftX - fontMetrics.stringWidth(string))/2 + leftX, y);

    }
    public boolean contains(ArrayList<String> list, String element){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).equals(element)){
                return true;
            }
        }
        return false;
    }

    public Font getFont(String fileName, float size){
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(fileName)).deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.PLAIN,new File(fileName)));
        } catch (IOException | FontFormatException e){
            font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
        }
        return font;
    }

    private class MyMouseListener implements MouseListener {

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

    } //end of mouselistener

}
