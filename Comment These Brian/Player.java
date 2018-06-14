/**
 * [Player.java]
 * This program (player object) represents a player that is in a game of Polytopia
 * Authors: Katelyn Wang & Brian Li
 * June 14 2018
 */

//Utility imports
import java.util.ArrayList;
import java.util.Iterator; //When does the iterator reset to start

public class Player {
    private String name; //Player's name
    private int tribe; //Player's tribe
    private int currency; //Amount of currency (stars) that the player has
    private ArrayList<String> technology = new ArrayList<String>(); //The technology (names in relation to the tech tree) that the player has
    private ArrayList<String> practical = new ArrayList<String>(); //The technology (names in relation to corresponding class names) that the player has
    private ArrayList<City> cities = new ArrayList<City>(); //Cities that the player has under their control

    private Interactions interaction; //
    private boolean unitSelected; //
    private boolean resourceSelected; //
    private boolean citySelected; //
    private boolean landSelected; //
    private boolean confirmAction; //
    private boolean turnEnd = false; //Flag indicating whether the player's turn has ended (for the turn method)
    private int selMapRow; //Row coordinate of the tile selected
    private int selMapCol; //Column coordinate of the tile selected
 
    private int numCities = 1; //Number of cities the player has under their control
    private int tierOneCost; //Currency cost of purchasing a tier one technology
    private int tierTwoCost; //Currency cost of purchasing a tier two technology

    boolean[][] mask; //The masked area that the player cannot see
    private Interactions handler; //Interactions object to perform map interactions in-game

    /**
     * Player
     * This constructor creates a player object with the designed city, interactions object, length of the map, and name of the player
     * @param A city object representing the first city (capital city) of the player (city)
     * @param An integer representing the player's tribe (tribe)
     * @param An interactions object to perform map interactions (interaction)
     * @param An integer to represent the length of the map (mapLength)
     * @param A string to represent the player's inputted name (name)
     */
    public Player(City city, int tribe, Interactions interaction, int mapLength, String name) {
        this.tribe = tribe; //Set tribe
        this.interaction = interaction; //Set interactions object
        this.name = name; //Set player name
        cities.add(city); //Add the capital city
        mask = new boolean[mapLength][mapLength]; //Set the player's mask to the specified size

        //initialize the map to cover most of the map
        for (int i = 0; i < mapLength; i++) {
            for (int j = 0; j < mapLength; j++) {
                if ((Math.abs(i - city.getRow()) < 3) && (Math.abs(j - city.getCol()) < 3)) {
                    mask[i][j] = false; //Set the mask to false (not concealed) if within range of the player's capital city
                } else {
                    mask[i][j] = true; //Set the mask to true (visible) elsewhere
                }
            }
        }

        currency = 5; //Set the player's initial currency to 5 at the start of the game
        tierOneCost = 5; //Set the cost of tier one technology to 5 currency units (stars)
        tierTwoCost = 7; //Set the cost of tier two technology to 7 currency units (stars)

       addPractical("Grass"); //Make all players capable of moving their units on grass
       addPractical("Warrior"); //Make all players capable of training warriors
       addTechnology("StartingTech"); //Give players the starting technology

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

    /**
     *
     */
    public void turnCurrencyIncrease() { //Calculate star increase for a given turn (at a given moment)
        int currencyIncrease = 0;
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).isCapital()) {
                currencyIncrease += (cities.get(i).getLevel() + 1);
            } else {
                currencyIncrease += cities.get(i).getLevel();
            }
        }
        currency += currencyIncrease;
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
     * setNumCities
     * This method sets the number of cities under the player's control
     * @param numCities, an integer representing the number of cities
     * @return nothing
     */
    public void setNumCities(int numCities){
        this.numCities = numCities; //Set the number of cities
    }

    /**
     * getNumCities
     * This method returns the number of cities under the player's control
     * @return numCities, the number of cities the player has
     */
    public int getNumCities(){
        return this.numCities; //Return the number of cities
    }
}
