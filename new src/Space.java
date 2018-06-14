/**
 *
 */
public class Space{
    private Unit unit;
    private Terrain terrain;
    private City city;
    private Resource resource;

    /**
     *
     * @param terrain
     */
    public Space(Terrain terrain){
        this.terrain = terrain;
    }

    /**
     *
     * @param terrain
     * @param unit
     */
    public Space(Terrain terrain, Unit unit){
        this.terrain = terrain;
        this.unit = unit;
    }

    /**
     *
     * @param terrain
     * @param city
     */
    public Space(Terrain terrain, City city){
        this.terrain = terrain;
        this.city = city;
    }

    /**
     *
     * @param terrain
     * @param resource
     */
    public Space(Terrain terrain, Resource resource){
        this.terrain = terrain;
        this.resource = resource;
    }

    /**
     *
     * @param unit
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    /**
     *
     * @param terrain
     */
    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    /**
     *
     * @return
     */
    public Unit getUnit() {
        return unit;
    }

    /**
     *
     * @return
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     *
     * @return
     */
    public String getTerrainName(){
        return terrain.getClass().getSimpleName();
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
    public Resource getResource() {
        return resource;
    }

    /**
     *
     * @param resource
     */
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    /**
     *
     * @return
     */
    public boolean containsResource(){
        if (resource != null){
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean containsUnit(){
        if (unit != null){
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean containsCity(){
        if (city != null){
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty(){
        return((city == null) && (unit == null) && (resource == null));
    }
}
