import java.util.ArrayList;
import java.util.Iterator; //When does the iterator reset to start

public class Player {
    private int tribe;
    private int currency;
    private ArrayList<String> technology = new ArrayList<String>();
    private ArrayList<City> cities = new ArrayList<City>();
    private boolean[] units = new boolean[9];
    protected Iterator<City> itr = cities.iterator();

    private Interactions interaction;
    private boolean unitSelected;
    private boolean resourceSelected;
    private boolean citySelected;
    private boolean landSelected;
    private boolean confirmAction;
    private boolean turnEnd;
    private int selMapRow;
    private int selMapCol;

    boolean[][] mask;
    private Interactions handler;

    public Player(City city, int tribe, Interactions interaction, int mapLength) {
        this.tribe = tribe;
        this.interaction = interaction;
        cities.add(city);
        mask = new boolean[mapLength][mapLength];

        //initialize the map to cover most of the map
        for (int i = 0; i < mapLength; i++) {
            for (int j = 0; j < mapLength; j++) {
                if ((Math.abs(i - city.getR()) < 3) && (Math.abs(j - city.getC()) < 3)) {
                    mask[i][j] = false;
                } else {
                    mask[i][j] = true;
                }
            }
        }
        
        currency = 0;
        
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
            for (int i = 0; i < cities.size(); i++) {
                if (cities.get(i).isCapital()) {
                    currencyIncrease += (cities.get(i).getLevel() + 1);
                } else {
                    currencyIncrease += cities.get(i).getLevel();
                }
            }
            currency += currencyIncrease;
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

        public boolean[][] getMask() {
          return mask;
        }
        
        public void turn(){
            while(!turnEnd){
                if (citySelected){
                //    interaction.displayUnits();
                }
               // if (confirmAction()){
                 //   if (){
                   // }
               // }
            }
        }
    }