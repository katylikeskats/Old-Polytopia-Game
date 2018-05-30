abstract public class Terrain {
    int r;
    int c;
    Item item;
    double defenseMultiplier;

    //Constructor if it does have an item, remove if never used (basically useless)
    Terrain(int r, int c, Item item){
        this.r = r;
        this.c = c;
        this.item = item;
        defenseMultiplier = 1;
    }

    //Constructor if it has no item
    Terrain(int r, int c){
        this.r = r;
        this.c = c;
        defenseMultiplier = 1;
    }

    public void addItem(Item item){
        this.item = item;
    }

    public boolean hasItem(){
        if (item != null){
            return true;
        }
        return false;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getDefenseMultiplier() {
        return defenseMultiplier;
    }

    public void setDefenseMultiplier(double defenseMultiplier) {
        this.defenseMultiplier = defenseMultiplier;
    }

}
