import java.util.ArrayList;
import java.util.Iterator; //When does the iterator reset to start

public class Player {
    String tribe;
    int currency;
    ArrayList<String> technology;
    ArrayList<City> cities = new ArrayList<City>();
    Iterator<City> itr = cities.iterator();
    boolean[][] mask;
    Interactions handler;

    Player(String tribe, Space[][] map){
        this.tribe = tribe;
        mask = new boolean[map.length][map.length];
        handler = new Interactions(map);
    }
    
    public void addCity(int r, int c) {
      cities.add(new City(r, c, false));
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
    
}
