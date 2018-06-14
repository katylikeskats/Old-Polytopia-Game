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
     * addTechnology
     * This method adds the technology that a player has bought to their list of owned technology names
     * @param technology, a string representing the name of the technology being added
     * @return nothing
     */
    public void addTechnology(String technology){
        this.technology.add(technology); //Add the technology
    }

    /**
     * addTechnology
     * This method adds the practical (corresponding class name) of the technology that a player has bought to their list of owned technology names
     * @param technology, a string representing the name of the class corresponding to the technology being added
     * @return nothing
     */
    public void addPractical(String technology){
        this.practical.add(technology); //Add the class name
    }

    /**
     * turnCurrencyIncrease
     * This method calculates the currency increase of a player at the start of their turn given the number of and level of cities, and adds it to the player's current currency value
     * @param nothing
     * @return nothing
     */
    public void turnCurrencyIncrease() { //Calculate star increase for a given turn (at a given moment)
        int currencyIncrease = 0;
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).isCapital()) {
                currencyIncrease += (cities.get(i).getLevel() + 1); //Increase currencyIncrease by 1 more than the city's level for a capital city
            } else {
                currencyIncrease += cities.get(i).getLevel(); //Increase currencyIncrease by the city's level for a non-capital city
            }
        }
        currency += currencyIncrease; //Add to the player's currency
    }

    /**
     * getTribe
     * This method returns the tribe of the player
     * @param nothing
     * @return An integer representing the player's tribe
     */
    public int getTribe() {
        return tribe; //Return the tribe
    }

    /**
     * setTribe
     * This method sets the tribe of a player
     * @param tribe, An integer representing the tribe being set
     * @return nothing
     */
    public void setTribe(int tribe) {
        this.tribe = tribe; //Set the tribe
    }

    /**
     * getCurrency
     * This method returns the player's currency
     * @param nothing
     * @return An integer representing the player's currency
     */
    public int getCurrency() {
        return currency; //Return the currency
    }

    /**
     * setCurrency
     * This method sets a player's currency
     * @param currency, an integer representing the currency being set
     * @return nothing
     */
    public void setCurrency(int currency) {
        this.currency = currency; //Set the currency
    }

    /**
     * getTechnology
     * This method returns the list of a player's technology
     * @param nothing
     * @return technology, the list of a player's technology (tech tree name)
     */
    public ArrayList<String> getTechnology() {
        return technology; //Return the technology
    }

    /**
     * setTechnology
     * This method sets a player's list of technology
     * @param technology
     * @return nothing
     */
    public void setTechnology(ArrayList<String> technology) {
        this.technology = technology;
    }

    /**
     * getMask
     * This method returns a player's array of masked spaces
     * @param nothing
     * @return the player's mask array
     */
    public boolean[][] getMask() {
        return mask; //Return the mask array
    }

    /**
     * turn
     * This method runs a player's turn
     * @param nothing
     * @return nothing
     */
    public void turn(){
        interaction.cityCapturingCheck(this); //Check if any cities are to be captured by the player at the start of the turn
        while(!turnEnd){
            try {Thread.sleep(500);} catch (InterruptedException e){
            }
        }
        interaction.resetMove();
    }

    /**
     * getTierOneCost
     * This method returns the cost of tier one technology
     * @param nothing
     * @return an integer representing the currency cost of tier one technology
     */
    public int getTierOneCost() {
        return tierOneCost;
    }

    /**
     * setTierOneCost
     * This method sets the cost of tier one technology
     * @param tierOneCost, the currency cost of tier one technology
     * @return nothing
     */
    public void setTierOneCost(int tierOneCost) {
        this.tierOneCost = tierOneCost;
    }

    /**
     * getTierTwoCost
     * This method returns the cost of tier two technology
     * @param nothing
     * @return an integer representing the currency cost of tier two technology
     */
    public int getTierTwoCost() {
        return tierTwoCost;
    }

    /**
     * setTierTwoCost
     * This method sets the cost of tier two technology
     * @param tierTwoCost, the currency cost of tier two technology
     * @return nothing
     */
    public void setTierTwoCost(int tierTwoCost) {
        this.tierTwoCost = tierTwoCost;
    }

    /**
     * setTurnEnd
     * This method sets a player's turn to end
     * @param turnEnd, which flags whether the player's turn is to end
     * @return nothing
     */
    public void setTurnEnd(boolean turnEnd) {
        this.turnEnd = turnEnd;
    }

    /**
     * getName
     * This method returns the player's name
     * @param nothing
     * @return A string representing the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * getPractical
     * This method returns the list of corresponding class names to technology owned
     * @return An arraylist representing the above
     */
    public ArrayList<String> getPractical(){
        return this.practical;
    }

    /**
     * increaseCosts
     * This method increases the costs of technology
     * @param nothing
     * @return nothing
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
