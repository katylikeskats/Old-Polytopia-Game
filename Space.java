public class Space{
    private Unit unit;
    private Terrain terrain;
    private City city;
    Space(Unit unit, Terrain terrain){
        this.unit = unit;
        this.terrain = terrain;
    }

    Space(Unit unit, City city){
        this.unit = unit;
        this.city = city;
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

}
