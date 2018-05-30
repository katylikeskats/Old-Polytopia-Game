public class Fruit extends Item {
    private int popIncrease;
    private int cost;

    Fruit(int r, int c, String tribe){
        super(r, c);
        popIncrease = 1;
        cost = 2;
    }
}
