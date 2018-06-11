//make iterator
public class City{
    private int r;
    private int c;
    private boolean capital;
    private int cityRadius;
    private int maxPop;
    private int maxUnits;
    private int currPop;
    private int currUnits;
    private int level;
    private int tribe;
    private int order;
    private Player player; //Do we need a player (isn't tribe enough)
    
    //Constructor
    public City(int r, int c, boolean capital){
        this.r = r;
        this.c = c;
        this.capital = capital;
        this.cityRadius = 1;
        this.maxUnits = 2;
        this.currUnits = 0;
        this.maxPop = 2;
        this.currPop = 0;
        this.level = 1;
    }

    public City(int r, int c, boolean capital, Player player){ //idk if we need this one
        this.r = r;
        this.c = c;
        this.capital = capital;
        this.cityRadius = 1;
        this.maxUnits = 2;
        this.maxPop = 2;
        this.currPop = 0;
        this.level = 1;
        this.player = player;
    }

    /**
     *
     * @return
     */
    public boolean isCapital() {
        return capital;
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

    public int getTribe() {
        return tribe;
    }

    public void setTribe(int tribe) {
        this.tribe = tribe;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     *
     */
    public void levelUp(){
       this.level++;
       //this.currPop = 0; This causes errors in the currPop calc in increasePop (don't need)
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
            currPop = (currPop + num) - (maxPop - 1);
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


