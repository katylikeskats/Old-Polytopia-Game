public class Ninja extends Unit {
    private static int cost = 3;

    Ninja(int r, int c, int tribe, City city){
        super(r, c, 2, 3, 10, 3, 2, tribe, city);
    }

    public static int getCost(){
        return cost;
    }
}
