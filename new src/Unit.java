/**
 * [Unit.java]
 *
 * @author
 * June 14 2018
 */
abstract public class Unit {
    private int r;
    private int c;
    private int maxHealth;
    private int currHealth;
    private int movement;
    private int attack;
    private int defense;
    private int range;
    private int tribe;
    private int killCount;
    private City city;
    private boolean moved; //To indicate if a unit has moved already in a turn (set to having been moved when created)

    /**
     *
     * @param r
     * @param c
     * @param attack
     * @param defense
     * @param health
     * @param movement
     * @param range
     * @param tribe
     * @param city
     */
    public Unit(int r, int c, int attack, int defense, int health, int movement, int range, int tribe, City city){
        this.r = r;
        this.c = c;
        this.attack = attack;
        this.defense = defense;
        this.currHealth = health;
        this.maxHealth = health;
        this.movement = movement;
        this.range = range;
        this.tribe = tribe;
        this.city = city;
        //this.moved = true;
    }

    /**
     *
     * @return
     */
    public boolean getMoved() {
        return moved;
    }

    /**
     *
     * @param moved
     */
    public void setMoved(boolean moved) {
        this.moved = moved;
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
    public City getCity() { return city; }

    /**
     *
     * @param city
     */
    public void setCity(City city) {
        this.city = city;
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
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     *
     * @param health
     */
    public void setMaxHealth(int health) {
        this.maxHealth = health;
    }

    /**
     *
     * @return
     */
    public int getCurrHealth() {
        return currHealth;
    }

    /**
     *
     * @param currHealth
     */
    public void setCurrHealth(int currHealth) {
        this.currHealth = currHealth;
    }

    /**
     *
     * @return
     */
    public int getMovement() {
        return movement;
    }

    /**
     *
     * @param movement
     */
    public void setMovement(int movement) {
        this.movement = movement;
    }

    /**
     *
     * @return
     */
    public int getAttack() {
        return attack;
    }

    /**
     *
     * @param attack
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /**
     *
     * @return
     */
    public int getDefense() {
        return defense;
    }

    /**
     *
     * @return
     */
    public int getRange() {
        return range;
    }

    /**
     *
     * @return
     */
    public int getTribe() {
        return tribe;
    }

    /**
     *
     * @param tribe
     */
    public void setTribe(int tribe) {
        this.tribe = tribe;
    }

    //never really used this did we?
    public int getKillCount() {
        return killCount;
    }

    /**
     *
     * @param killCount
     */
    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    /**
     *
     * @param other
     * @return
     */
    public int getDamage(Unit other) {
        double attackForce = this.attack * (this.currHealth / this.maxHealth);
        double defenseForce = other.getDefense() * (other.getCurrHealth() / other.getMaxHealth());
        double totalDamage = attackForce + defenseForce;
        return (int) Math.round((attackForce / totalDamage) * this.attack * 4.5);
    }

    /**
     *
     */
    public void levelUp() {
        this.currHealth = maxHealth + 5;
        this.maxHealth += 5;
        this.killCount = 0;
    }
}