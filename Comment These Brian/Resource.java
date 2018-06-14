/**
 * [Resource.java]
 *
 * @author
 * June 14 2018
 */

import java.util.ArrayList;

abstract public class Resource {
    private int r;
    private int c;
    private City city;
    private int popIncrease;
    private int cost;
    private ArrayList<String> options = new ArrayList<String>();

    /**
     *
     * @param r
     * @param c
     * @param popIncrease
     * @param cost
     */
    public Resource(int r, int c, int popIncrease, int cost){
        this.r = r;
        this.c = c;
        this.popIncrease = popIncrease;
        this.cost = cost;
    }

    /**
     *
     * @return
     */
    public City getCity() {
        return city;
    }

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
    public int getPopIncrease() {
        return popIncrease;
    }

    /**
     *
     * @return
     */
    public int getCost() {
        return cost;
    }

    /**
     *
     * @param cost
     */
    public void setCost(int cost) {
        this.cost = cost;
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

}
