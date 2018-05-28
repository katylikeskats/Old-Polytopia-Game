public class City {
    private int r;
    private int c;
    private boolean capital;
    private int cityRadius;
    private int maxPop;
    private int maxUnits;
    private int currPop;
    private int currUnits;
    private int level;

    //Constructor
    City(int r, int c, boolean capital){
        this.r = r;
        this.c = c;
        this.capital = capital;
        this.cityRadius = 1;
        this.maxUnits = 2;
        this.maxPop = 2;
        this.currPop = 0;
        this.currPop = 0;
        this.level = 1;
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

    /**
     *
     * @return
     */
    public boolean isCapital() {
        return capital;
    }

    /**
     *
     * @param capital
     */
    public void setCapital(boolean capital) {
        this.capital = capital;
    }

    /**
     *
     * @return
     */
    public int getCityRadius() {
        return cityRadius;
    }

    /**
     *
     * @param cityRadius
     */
    public void setCityRadius(int cityRadius) {
        this.cityRadius = cityRadius;
    }

    /**
     *
     * @return
     */
    public int getMaxPop() {
        return maxPop;
    }

    /**
     *
     * @param maxPop
     */
    public void setMaxPop(int maxPop) {
        this.maxPop = maxPop;
    }

    /**
     *
     * @return
     */
    public int getMaxUnits() {
        return maxUnits;
    }

    /**
     *
     * @param maxUnits
     */
    public void setMaxUnits(int maxUnits) {
        this.maxUnits = maxUnits;
    }

    /**
     *
     * @return
     */
    public int getCurrPop() {
        return currPop;
    }

    /**
     *
     * @param currPop
     */
    public void setCurrPop(int currPop) {
        this.currPop = currPop;
    }

    /**
     *
     * @return
     */
    public int getCurrUnits() {
        return currUnits;
    }

    /**
     *
     * @param currUnits
     */
    public void setCurrUnits(int currUnits) {
        this.currUnits = currUnits;
    }

    /**
     *
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     *
     */
    public void levelUp(){
       this.level++;
       this.currPop = 0;
       this.maxPop++;
       this.maxUnits++;
       //call on level up bonus offer
    }

    /**
     *
     * @param num
     */
    public void increasePop(int num){
        if ((currPop + num) < maxPop) {
            this.currPop += num;
        } else {
            levelUp();
            currPop = (currPop + num) - maxPop;
        }
    }

    /**
     * Determines if there is available slots to create a new unit
     * @return true if there is space to create a new unit, false if there is no space
     */
    public boolean hasSpace(){
        if (currUnits<maxUnits){
            return true;
        }
        return false;
    }

}


