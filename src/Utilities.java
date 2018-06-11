import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Utilities {
    public static Font getFont(String fileName, float size){
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

    public static boolean contains(ArrayList<String> list, String element){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).equals(element)){
                return true;
            }
        }
        return false;
    }

    public static boolean contains(String[] list, String element){
        for (int i = 0; i < list.length; i++){
            if (list[i].equals(element)){
                return true;
            }
        }
        return false;
    }
}
