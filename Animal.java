public class Animal extends Item{
    private int popIncrease;
    private int cost;

    Animal(int r, int c, String tribe){
        super(r, c);
        popIncrease = 1;
        cost = 2;
    }
}
