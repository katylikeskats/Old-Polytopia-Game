public class Mindbender extends Unit{
    private static int cost = 5;

    Mindbender(int r, int c, int tribe, City city){
        super(r, c, 0, 0, 10, 1, 1, tribe, city);
    }

    public static int getCost(){
        return cost;
    }
}
