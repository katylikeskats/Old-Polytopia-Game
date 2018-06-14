public class Knight extends Unit {
    private static int cost = 8;

    Knight(int r, int c, int tribe, City city){
        super(r, c, 6, 1, 15, 3, 1, tribe, city);
    }

    public static int getCost(){
        return cost;
    }
}
