/**
 * [Space.java]
 * This program represents a space object on polytopia's map that can hold a unit, terrain, city, and resource
 * Authors: Katelyn Wang & Brian Li
 * Date: June 14, 2018
 */
public class Space{
    private Unit unit; //Unit object on the space on the map
    private Terrain terrain; //Terrain object on the space on the map
    private City city; //City object on the space on the map
    private Resource resource; //Resource object on the space on the map

    /**
     * Space
     * This constructor creates a space object with the given terrain object
     * @param A terrain object representing the terrain of the space (tile)
     * @return nothing
     */
    public Space(Terrain terrain){
        this.terrain = terrain; //Set the terrain of the space
    }

    /**
     * Space
     * This constructor creates a space object with the given terrain and unit objects
     * @param A terrain object representing the terrain of the space (tile)
     * @param A unit object representing the unit that the space (tile) holds
     * @return nothing
     */
    public Space(Terrain terrain, Unit unit){
        this.terrain = terrain; //Set the terrain
        this.unit = unit; //Set the unit
    }

    /**
     * Space
     * This constructor creates a space object with the given terrain and city objects
     * @param A terrain object representing the terrain of the space (tile)
     * @param A city object representing the city held by the space (tile)
     * @return nothing
     */
    public Space(Terrain terrain, City city){
        this.terrain = terrain; //Set the terrain
        this.city = city; //Set the city
    }

    /**
     * Space
     * This constructor creates a space object with the given terrain and resource objects
     * @param A terrain object representing the terrain of the space (tile)
     * @param A resource object representing the resource held by the space (tile)
     * @return nothing
     */
    public Space(Terrain terrain, Resource resource){
        this.terrain = terrain; //Set the terrain
        this.resource = resource; //Set the resource
    }

    /**
     * setUnit
     * This method sets the unit held by the space on the map
     * @param A unit object to be set on the space
     * @return nothing
     */
    public void setUnit(Unit unit) {
        this.unit = unit; //Set the unit
    }

    /**
     * setTerrain
     * This method sets the terrain held by the space on the map
     * @param A terrain object to be set on the space
     * @return nothing
     */
    public void setTerrain(Terrain terrain) {
        this.terrain = terrain; //Set the terrain
    }

    /**
     * getUnit
     * This method returns the unit held by the space (tile)
     * @param nothing
     * @return A unit object representing the unit in the space on the map
     */
    public Unit getUnit() {
        return unit; //Get the unit
    }

    /**
     * getTerrain
     * This method returns the terrain held by the space (tile)
     * @param nothing
     * @return A terrain object representing the terrain in the space on the map
     */
    public Terrain getTerrain() {
        return terrain; //Get the terrain
    }

    /**
     * getTerrainName
     * This method returns the name of the terrain held by the space (tile)
     * @param nothing
     * @return A String representing the name of the terrain in the space on the map
     */
    public String getTerrainName(){
        return terrain.getClass().getSimpleName(); //Return the class name of the terrain
    }

    /**
     * getCity
     * This method returns the city held by the space (tile)
     * @param nothing
     * @return A city object representing the city in the space on the map
     */
    public City getCity() {
        return city; //Get the city
    }

    /**
     * setCity
     * This method sets the city held by the space on the map
     * @param A city object to be set on the space
     * @return nothing
     */
    public void setCity(City city) {
        this.city = city; //Set the city
    }

    /**
     * getResource
     * This method returns the resource held by the space object
     * @param nothing
     * @return A resource object representing the resource held in the space
     */
    public Resource getResource() {
        return resource; //Return the resource
    }

    /**
     * setResource
     * This method sets the resource held by the space
     * @param A resource object representing the resource that is to be set in the space
     * @return nothing
     */
    public void setResource(Resource resource) {
        this.resource = resource; //Set the resource
    }

    /**
     * containsResource
     * This method determines whether the space has a resource in it
     * @param nothing
     * @return a boolean, true if the space has a resource in it, false if not
     */
    public boolean containsResource(){
        if (resource != null){
            return true; //True if a resource exists in it
        }
        return false; //False if there is no unit
    }

    /**
     * containsUnit
     * This method determines whether the space has a unit in it
     * @param nothing
     * @return a boolean, true if the space has a unit in it, false if not
     */
    public boolean containsUnit(){
        if (unit != null){
            return true; //True if a unit exists in it
        }
        return false; //False if there is no unit
    }

    /**
     * containsCity
     * This method determines whether the space has a city in it
     * @param nothing
     * @return a boolean, true if the space has a city in it, false if not
     */
    public boolean containsCity(){
        if (city != null){
            return true; //True if a city in it exists
        }
        return false; //False if there is no city
    }

    /**
     * isEmpty
     * This method determines whether the space does not have a city, unit, or resource (all space objects must have terrain though)
     * @param nothing
     * @return a boolean, true if there are none of the three objects, false if otherwise
     */
    public boolean isEmpty(){
        return((city == null) && (unit == null) && (resource == null)); //Return whether there is no city, unit, and resource
    }
}
