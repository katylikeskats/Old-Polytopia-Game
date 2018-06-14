/**
 *
 */
public class Swordsperson extends Unit {
    private static int cost = 5;

    /**
     *
     * @param r
     * @param c
     * @param tribe
     * @param city
     */
    Swordsperson(int r, int c, int tribe, City city){
        super(r, c, 3, 3, 15, 1, 1, tribe, city);
    }

    /**
     *
     * @return
     */
    public static int getCost(){
        return cost;
    }
}
