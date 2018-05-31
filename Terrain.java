abstract public class Terrain {
    int r;
    int c;
    double defenseMultiplier;

    Terrain(int r, int c){
        this.r = r;
        this.c = c;
        defenseMultiplier = 1;
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

    public double getDefenseMultiplier() {
        return defenseMultiplier;
    }

    public void setDefenseMultiplier(double defenseMultiplier) {
        this.defenseMultiplier = defenseMultiplier;
    }

}
