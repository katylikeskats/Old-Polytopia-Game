public class Space{
    private Unit unit;
    private Terrain terrain;
    private City city;
    private Resource resource;

    public Space(Terrain terrain){
        this.terrain = terrain;
    }

    public Space(Terrain terrain, Unit unit){
        this.terrain = terrain;
        this.unit = unit;
    }

    public Space(Terrain terrain, City city){
        this.terrain = terrain;
        this.city = city;
    }

    public Space(Terrain terrain, Resource resource){
        this.terrain = terrain;
        this.resource = resource;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Unit getUnit() {
        return unit;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public String getUnitName(){
        return unit.getClass().getSimpleName();
    }

    public String getTerrainName(){
        return terrain.getClass().getSimpleName();
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public boolean containsResource(){
        if (resource != null){
            return true;
        }
        return false;
    }

    public boolean containsUnit(){
        if (unit != null){
            return true;
        }
        return false;
    }

    public boolean containsCity(){
        if (city != null){
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        return((city == null) && (unit == null) && (resource == null));
    }
}
