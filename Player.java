import java.util.ArrayList;
import java.util.Iterator; //When does the iterator reset to start

public class Player {
    private int tribe;
    private int currency;
    private ArrayList<String> technology = new ArrayList<String>();
    private ArrayList<City> cities = new ArrayList<City>();
    private boolean[] units = new boolean[9];
    protected Iterator<City> itr = cities.iterator();
    boolean[][] mask;
    private Interactions handler;

    public Player(int tribe, Map map){
        this.tribe = tribe;
        mask = new boolean[map.getMap().length][map.getMap().length];
        handler = new Interactions(map);
    }

    public void addTechnology(String technology){
        this.technology.add(technology);
    }

    public void addUnit(int unit){
        units[unit] = true;
    }

    public void addCity(int r, int c) {
        cities.add(new City(r, c, false, this));
    }

    public void removeCity(City desiredCity) {
        City city;
        while (itr.hasNext()) {
            city = itr.next();
            if ((desiredCity.getR() == city.getR()) && (desiredCity.getC() == city.getC())) {
                cities.remove(city);
            }
        }
    }

    public void turnCurrencyIncrease() { //Calculate star increase for a given turn (at a given moment)
        int currencyIncrease = 0;
        City city;
        while (itr.hasNext()) {
            city = itr.next();
            if (city.isCapital()) {
                currencyIncrease += (city.getLevel() + 1);
            } else {
                currencyIncrease += city.getLevel();
            }
        }
        currency += currencyIncrease;
    }

    public void turn() {

    }

    public int getTribe() {
        return tribe;
    }

    public void setTribe(int tribe) {
        this.tribe = tribe;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public ArrayList<String> getTechnology() {
        return technology;
    }

    public void setTechnology(ArrayList<String> technology) {
        this.technology = technology;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public boolean[] getUnits() {
        return units;
    }

    public void setUnits(boolean[] units) {
        this.units = units;
    }


}