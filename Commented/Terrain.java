/**
 * [Terrain.java]
 * Terrain class (abstract object) that represents a tile of terrain on polytopia's map
 * Authors: Katelyn Wang & Brian Li
 * Date: June 14, 2018
 */
abstract public class Terrain {
    int r; //Row coordinate
    int c; //Column coordinate
    double defenseMultiplier; //Value that stores a multiplier used when calculating battles with certain terrain

    /**
     * Terrain
     * Constructor that creates a terrain object (although never used just by itself since terrain is abstract) 
     *  that makes a terrain object with the given row and column coordinates in relation to the game's map
     * @param An integer representing the object's row coordinate
     * @param An integer representing the object's column coordinate
     * @return nothing
     */
    Terrain(int r, int c){
        this.r = r; //Set the row coordinate
        this.c = c; //Set the column coordinate
        defenseMultiplier = 1; //Set the value of the defense multiplier (which is terrain-dependent)
    }

    /**
     * getR
     * This method returns the row coordinate of the terrain object in relation to the game's map
     * @param nothing
     * @return an integer representing the object's row coordinate
     */
    public int getR() {
        return r; //Get the row coordinate
    }

    /**
     * setR
     * This method sets the row coordinate of the terrain object in relation to the game's map
     * @param An integer that represents the object's row coordinate (that is being set)
     * @return nothing
     */
    public void setR(int r) {
        this.r = r; //Set the row coordinate
    }

    /**
     * getC
     * This method returns the column coordinate of the terrain object in relation to the game's map
     * @param nothing
     * @return an integer representing the object's column coordinate
     */
    public int getC() {
        return c; //Get the column coordinate
    }

    /**
     * setC
     * This method sets the column coordinate of the terrain object in relation to the game's map
     * @param An integer that represents the object's column coordinate (that is being set)
     * @return nothing
     */
    public void setC(int c) {
        this.c = c; //Set the column coordinate
    }

}
