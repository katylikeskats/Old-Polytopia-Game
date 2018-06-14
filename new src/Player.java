/**
 * [Player.java]
 *
 * @author
 * June 14 2018
 */

//Utility imports
import java.util.ArrayList;
import java.util.Iterator; //When does the iterator reset to start

public class Player {
    private String name;
    private int tribe;
    private int currency;
    private ArrayList<String> technology = new ArrayList<String>();
    private ArrayList<String> practical = new ArrayList<String>();
    private ArrayList<City> cities = new ArrayList<City>();

    private Interactions interaction;
    private boolean unitSelected;
    private boolean resourceSelected;
    private boolean citySelected;
    private boolean landSelected;
    private boolean confirmAction;
    private boolean turnEnd = false;
    private int inGameBonus = 0;
    private int mapLength;
    private int numCities = 1;
    private int tierOneCost;
    private int tierTwoCost;

    boolean[][] mask;
    private Interactions handler;

    /**
     *
     * @param city
     * @param tribe
     * @param interaction
     * @param mapLength
     * @param name
     */
    public Player(City city, int tribe, Interactions interaction, int mapLength, String name) {
        this.tribe = tribe;
        this.interaction = interaction;
        this.name = name;
        this.mapLength = mapLength;
        cities.add(city);
        mask = new boolean[mapLength][mapLength];

        //initialize the map to cover most of the map
        for (int i = 0; i < mapLength; i++) {
            for (int j = 0; j < mapLength; j++) {
                if ((Math.abs(i - city.getRow()) < 3) && (Math.abs(j - city.getCol()) < 3)) {
                    mask[i][j] = false;
                } else {
                    mask[i][j] = true;
                }
            }
        }

        currency = 5;
        tierOneCost = 5;
        tierTwoCost = 7;

       addPractical("Grass");
       addPractical("Port");
        addPractical("Warrior");
       addTechnology("StartingTech");

    }

    /**
     *
     * @param technology
     */
    public void addTechnology(String technology){
        this.technology.add(technology);
    }

    /**
     *
     * @param technology
     */
    public void addPractical(String technology){
        this.practical.add(technology);
    }

    public void addCity(City city){
        this.cities.add(city);
    }

    /**
     *
     */
    public void turnCurrencyIncrease() { //Calculate star increase for a given turn (at a given moment)
        currency += calculateCurrIncrease();
    }

    public int calculateCurrIncrease(){
        int currencyIncrease = 0;
        for (int i = 0; i < mapLength; i++) {
            for (int j = 0; j < mapLength; j++) {
                if (interaction.getTileMap()[i][j].getCity() != null) {
                    if (interaction.getTileMap()[i][j].getCity().getTribe() == this.tribe) {
                        if (interaction.getTileMap()[i][j].getCity().isCapital()) {
                            currencyIncrease += (interaction.getTileMap()[i][j].getCity().getLevel() + 1); //Increase currencyIncrease by 1 more than the city's level for a capital city
                        } else {
                            currencyIncrease += interaction.getTileMap()[i][j].getCity().getLevel(); //Increase currencyIncrease by the city's level for a non-capital city
                        }
                    }
                }
            }
        }
        return currencyIncrease + inGameBonus;
    }

    public void setInGameBonus(int inGameBonus){
        this.inGameBonus = inGameBonus;
    }

    public int getInGameBonus() {
        return this.inGameBonus;
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

    /**
     *
     * @return
     */
    public int getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     */
    public void setCurrency(int currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getTechnology() {
        return technology;
    }

    /**
     *
     * @param technology
     */
    public void setTechnology(ArrayList<String> technology) {
        this.technology = technology;
    }

    /**
     *
     * @return
     */
    public boolean[][] getMask() {
        return mask;
    }

    /**
     *
     */
    public void turn(){
        interaction.cityCapturingCheck(this);
        while(!turnEnd){
            try {Thread.sleep(500);} catch (InterruptedException e){
            }
        }
        interaction.resetMove();
    }

    /**
     *
     * @return
     */
    public int getTierOneCost() {
        return tierOneCost;
    }

    /**
     *
     * @param tierOneCost
     */
    public void setTierOneCost(int tierOneCost) {
        this.tierOneCost = tierOneCost;
    }

    /**
     *
     * @return
     */
    public int getTierTwoCost() {
        return tierTwoCost;
    }

    /**
     *
     * @param tierTwoCost
     */
    public void setTierTwoCost(int tierTwoCost) {
        this.tierTwoCost = tierTwoCost;
    }

    /**
     *
     * @param turnEnd
     */
    public void setTurnEnd(boolean turnEnd) {
        this.turnEnd = turnEnd;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getPractical(){
        return this.practical;
    }

    /**
     *
     */
    public void increaseCosts(){
        this.tierOneCost++;
        this.tierTwoCost++;
    }

    /**
     *
     * @param numCities
     */
    public void setNumCities(int numCities){
        this.numCities = numCities;
    }

    /**
     *
     * @return
     */
    public int getNumCities(){
        return this.numCities;
    }

    public Interactions getInteractions(){
        return this.interaction;
    }
}