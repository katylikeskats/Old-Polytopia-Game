/**
 * [Rider.java]
 *
 */
public class Rider extends Unit{
    private static int cost = 3;

    /**
     *
     * @param r
     * @param c
     * @param tribe
     * @param city
     */
    Rider(int r, int c, int tribe, City city){
        super(r, c, 2, 1, 10, 2, 1, tribe, city);
    }

    /**
     *
     * @return
     */
    public static int getCost(){
        return cost;
    }
}
