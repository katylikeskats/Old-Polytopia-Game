/**
 *
 */
abstract public class Terrain {
    int r;
    int c;
    double defenseMultiplier;

    /**
     *
     * @param r
     * @param c
     */
    Terrain(int r, int c){
        this.r = r;
        this.c = c;
        defenseMultiplier = 1;
    }

    /**
     *
     * @return
     */
    public int getR() {
        return r;
    }

    /**
     *
     * @param r
     */
    public void setR(int r) {
        this.r = r;
    }

    /**
     *
     * @return
     */
    public int getC() {
        return c;
    }

    /**
     *
     * @param c
     */
    public void setC(int c) {
        this.c = c;
    }

//we never really used this, should we remove it?
    public void setDefenseMultiplier(double defenseMultiplier) {
        this.defenseMultiplier = defenseMultiplier;
    }

}
