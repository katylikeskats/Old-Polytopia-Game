abstract public class Item {
    private int r;
    private int c;
    private int popIncrease;
    private int cost;
    private City city;

    public Item(int r, int c, int popIncrease, int cost){
        this.r = r;
        this.c = c;
        this.popIncrease = popIncrease;
        this.cost = cost;
    }

    /**public Item(int r, int c){
        this.r = r;
        this.c = c;
    }
     */

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getPopIncrease() {
        return popIncrease;
    }

    public void setPopIncrease(int popIncrease) {
        this.popIncrease = popIncrease;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

}
