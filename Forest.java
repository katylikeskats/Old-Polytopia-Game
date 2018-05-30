public class Forest extends Item{
    private int popIncrease;
    private int cost;

    Forest(int r, int c, String tribe){
        super(r, c);
        popIncrease = 1;
        cost = 2;
    }
}
