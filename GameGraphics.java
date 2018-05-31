import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Toolkit;

public class GameGraphics extends JPanel {
    Space[][] map;
    Interactions interactions;


    /*
    0 is oumanji's array
    1 is barur's array
    2 is xinxi's array
    3 is imperius's array
    4 is aquarion's array
     */

    //code for image = Toolkit.getDefaultToolkit().getImage("assets/image.jpg")
    private static Image water = Toolkit.getDefaultToolkit().getImage("assets/image.jpg");
    private static Image grass = Toolkit.getDefaultToolkit().getImage("assets/image.jpg");
    private static Image mountains = Toolkit.getDefaultToolkit().getImage("assets/image.jpg");
    private static Image redTarget;
    private static Image greyTarget;
    private static Image[][] units = {{Toolkit.getDefaultToolkit().getImage("assets/image.jpg")},
            {Toolkit.getDefaultToolkit().getImage("assets/image.jpg")},
            {Toolkit.getDefaultToolkit().getImage("assets/image.jpg")},
            {Toolkit.getDefaultToolkit().getImage("assets/image.jpg")},
            {Toolkit.getDefaultToolkit().getImage("assets/image.jpg")}};
    private static Image[][] resources = {{Toolkit.getDefaultToolkit().getImage("assets/image.jpg")},
            {Toolkit.getDefaultToolkit().getImage("assets/image.jpg")},
            {Toolkit.getDefaultToolkit().getImage("assets/image.jpg")},
            {Toolkit.getDefaultToolkit().getImage("assets/image.jpg")},
            {Toolkit.getDefaultToolkit().getImage("assets/image.jpg")}};
    private static Image[] technology = {Toolkit.getDefaultToolkit().getImage("assets/image.jpg")};
    private static Image[][] cities = {{Toolkit.getDefaultToolkit().getImage("assets/image.jpg")},
            {Toolkit.getDefaultToolkit().getImage("assets/image.jpg")},
            {Toolkit.getDefaultToolkit().getImage("assets/image.jpg")},
            {Toolkit.getDefaultToolkit().getImage("assets/image.jpg")},
            {Toolkit.getDefaultToolkit().getImage("assets/image.jpg")}};
    private static Image[][] resourceOptions = {{/*fruit*/}, {/*animal*/}, {/*forest*/}, {/*crop*/}, {/*fish*/}, {/*whale*/}};

    /*
    0 - fruit
    1 - animal
    2 - forest
    3 - crop
    4 - fish
    5 - whale
     */
    GameGraphics(Space[][] map){
        this.map = map;
    }

    public void displayOptions(Resource resource){

    }

    public void displayMoves(Graphics g){
        int currX = 1;
        int currY = 1;
        for (int i = -map[r][c].getUnit().getMovement(); i < map[r][c].getUnit().getMovement() + 1; i++){
            for (int j = -map[r][c].getUnit().getMovement(); j < map[r][c].getUnit().getMovement() + 1; j++) {
                if (interactions.validateMove(map[r][c].getUnit(), map[r][c].getUnit().getR() + i, map[r][c].getUnit().getC() + j)) {
                    g.drawImage(greyTarget, currX, currY, 10, 10, this);
                } else if (map[map[r][c].getUnit().getR() + i][map[r][c].getUnit().getC() + j].getUnit() != null){
                    g.drawImage(redTarget, currX, currY, 10, 10, this);
                }
                if (currX + 5 > 100){
                    currY += 10;
                    currX = 1;
                } else {
                    currX += 10;
                }
            }
        }
    }



    /*
    0 - warrior
    1 - swordsperson
    2 - defender
    3 - rider
    4 - knight
    5 - archer
    6 - catapult
    7 - mindbender
    8 - ninja
    */
    public void displayUnits(Player player, int tribe, Graphics g){
        int currX = 1;
        int currY = 1;
        for (int i = 0; i < player.getUnits().length; i++){
            if (player.getUnits()[i]) {
                g.drawImage(units[tribe][i], currX, currY, 10, 10, this);
                if (currX + 5 > 10){
                    currY += 10;
                    currX = 1;
                } else {
                    currX += 10;
                }
            }
        }
    }


}
