import java.util.ArrayList;

//Removing units from cities once they die?

public class Interactions {
    private Map map;

    public Interactions(Map map){
        this.map = map;
    }

    /**
     *
     * @param attacker
     * @param defender
     */
    public void attack(Unit attacker, Unit defender) {
        defender.setCurrHealth(defender.getCurrHealth() - attacker.getDamage(defender)); //Calculate damage done to the defender
        if ((defender.getCurrHealth() > 0) && (Math.abs(defender.getR() - attacker.getR()) <= defender.getRange()) && (Math.abs(defender.getC() - attacker.getC()) <= defender.getRange())) {
          attacker.setCurrHealth(attacker.getCurrHealth() - defender.getDamage(attacker)); //Counterattack only if the defender survives and they are in range of the attacker for their own attack
        }
        if (defender.getCurrHealth() <= 0) {
          die(defender); //Kill the defender
          attacker.setKillCount(attacker.getKillCount() + 1); //DO MOVEMENT AFTER ATTACKING
          if (attacker.getKillCount() == 3) {
            attacker.levelUp();
          }
        } else if (attacker.getCurrHealth() <= 0) {
          die(attacker); //Kill the attacker
          defender.setKillCount(defender.getKillCount() + 1);
          if (defender.getKillCount() == 3) {
            defender.levelUp();
          }
        }
    }

    private void die(Unit unit){
        City city = unit.getCity();
        city.setCurrUnits(city.getCurrUnits() - 1); //Decrease number of units of the city whose unit died
        map.getMap()[unit.getR()][unit.getC()].setUnit(null);
    }

    public boolean validateAttack(Unit unit, int newR, int newC, Player player) {
      if ((player.getTribe() != map.getMap()[newR][newC].getUnit().getTribe()) && (Math.abs(unit.getR() - newR) <= unit.getRange()) && (Math.abs(unit.getC() - newC) <= unit.getRange()) && (player.getTribe() == unit.getTribe())) {
      //check to make sure that the player's tribe is different from the unit on newR, newC's tribe, and that they unit being attacked is in range
        return true;
      } else {
        return false;
      }
    }
    
    public boolean validateMove(Unit unit, int newR, int newC, Player player, boolean[][] mask){
        return ((contains(player.getTechnology(), map.getMap()[newR][newC].getTerrainName())) && (newR >= 0) && (newC >= 0) && (newR < map.getMap().length) && (newC < map.getMap().length) && (map.getMap()[newR][newC].getUnit() == null) && (Math.abs(unit.getR() - newR) <= unit.getMovement()) && (Math.abs(unit.getC() - newC) <= unit.getMovement()) && !(mask[newR][newC]) && (player.getTribe() == unit.getTribe()));
    }

    public void move (Unit unit, int newR, int newC, boolean[][] mask){
      if ((map.getMap()[newR][newC].getTerrain() instanceof Port) && !(unit instanceof Boat)){
        map.getMap()[newR][newC].setUnit(new Boat(newR, newC, unit)); //Take into account prev unit's health?
        map.getMap()[unit.getR()][unit.getC()].setUnit(null);
        unit.setR(newR); //Change the position of the unit in the unit object
        unit.setC(newC);
      } else if ((unit instanceof Boat) && (map.getMap()[newR][newC].getTerrain() instanceof Water)) {
        map.getMap()[unit.getR()][unit.getC()].setUnit(null); //Move a boat normally (like a unit would on land) if it moves onto a water tile
        map.getMap()[newR][newC].setUnit(unit);
        unit.setR(newR); //Change the position of the unit in the unit object
        unit.setC(newC);
        ((Boat)(unit)).getUnitContained().setR(newR); //Set the unit inside's r value as well
        ((Boat)(unit)).getUnitContained().setC(newC); //Set the unit inside's c value as well
      } else if (unit instanceof Boat) {
        map.getMap()[unit.getR()][unit.getC()].setUnit(null); //If a boat moves onto a land tile, put the unit inside it onto the land tile
        map.getMap()[newR][newC].setUnit(((Boat)(unit)).getUnitContained()); //Cast a boat into the unit
        map.getMap()[newR][newC].getUnit().setR(newR); //Set the unit that was inside the boat's row and column values to the new ones
        map.getMap()[newR][newC].getUnit().setC(newC);
      } else {
        map.getMap()[unit.getR()][unit.getC()].setUnit(null); //Non-boat unit moving onto land
        map.getMap()[newR][newC].setUnit(unit);
        unit.setR(newR); //Change the position of the unit in the unit object
        unit.setC(newC);
      }
      for (int i = (newR - 1); i < (newR + 2); i++) {
          for (int j = (newC - 1); j < (newC + 2); j++) {
            if ((i >= 0) && (j >= 0) && (i < map.getMap().length) && (j < map.getMap().length)) {
              if (mask[i][j]) {
                mask[i][j] = false; //Undo the mask when explored by a unit (in proximity)
              }
            }
          }
        }
      if (map.getMap()[newR][newC].getCity() != null) {
        if (map.getMap()[newR][newC].getCity().getTribe() != unit.getTribe()) {
          map.getMap()[newR][newC].getCity().setCapturing(true); //Set a city to being captured if a unit of a different tribe has gone on it
        }
      }
    }

    public boolean contains(ArrayList<String> list, String item){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).equals(item)){
                return true;
            }
        }
        return false;
    }

    //ISSUE - After levelling up, it doesn't give the city population anymore???
    public boolean harvestItem (Player player, int r, int c){
      if (map.getMap()[r][c].getResource().getCity() != null) {
        if ((player.getCurrency() >= map.getMap()[r][c].getResource().getCost()) && (contains(player.getTechnology(), map.getMap()[r][c].getResource().getClass().getSimpleName())) &&(map.getMap()[r][c].getResource().getCity().getTribe() == player.getTribe())){
            player.setCurrency(player.getCurrency() - map.getMap()[r][c].getResource().getCost());
            map.getMap()[r][c].getResource().getCity().increasePop(map.getMap()[r][c].getResource().getPopIncrease());
            //System.out.println(map.getMap()[r][c].getResource().getCity().getCurrPop());
            map.getMap()[r][c].setResource(null);
            return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    }

    public void changeTribe(Mindbender mindbender, Unit unit){
        unit.setTribe(mindbender.getTribe());
    }
    
    public boolean trainUnit(Player player, String unitName, int r, int c) {
      if ((map.getMap()[r][c].getUnit() == null) && (player.getTribe() == map.getMap()[r][c].getCity().getTribe()) && (map.getMap()[r][c].getCity().getCurrUnits() != map.getMap()[r][c].getCity().getMaxUnits())) { //Can only create a unit if there's no unit on the city currently and the player training the unit owns the city and the city isn't full
        if (unitName == "Warrior") {
          if (player.getCurrency() >= Warrior.getCost()) {
            player.setCurrency(player.getCurrency() - Warrior.getCost());
            map.getMap()[r][c].getCity().setCurrUnits(map.getMap()[r][c].getCity().getCurrUnits() + 1);
            map.getMap()[r][c].setUnit(new Warrior(r, c, player.getTribe(), map.getMap()[r][c].getCity()));
            return true;
          }
        }
      }
      
      return false;
      
    }
    
    //CAPTURE CITY METHOD
    //NEED TO: Change the unit capturing it's city to the city that got captured, set the tribe ofthe city to the same as the player whose unit captured it
    private void captureCity(City city, Unit unit) {
      city.setTribe(unit.getTribe()); //Set the tribe of the city to that of the unit capturing it
      unit.getCity().setCurrUnits(unit.getCity().getCurrUnits() - 1); //Decrease number of units in the city that the unit capturing the new city was from
      unit.setCity(city); //Set the unit capturing the city's city to the city being captured
      unit.getCity().setCurrUnits(1);
    }
    
    //At the start of each player's turn, if the player's unit is on a city that is marked as being captured, capture the city
    public void cityCapturingCheck(Player player) {
      for (int i = 0; i < map.getMap().length; i++) {
        for (int j = 0; j < map.getMap().length; j++) {
          if (map.getMap()[i][j].getCity() != null) {
            if ((map.getMap()[i][j].getCity().getCapturing()) && (map.getMap()[i][j].getUnit() != null)) {
              if ((map.getMap()[i][j].getUnit().getTribe() != map.getMap()[i][j].getCity().getTribe()) && (map.getMap()[i][j].getUnit().getTribe() == player.getTribe())) {
                if (map.getMap()[i][j].getCity().getTribe() != 5) {
                  emptyCity(map.getMap()[i][j].getCity()); //Empty the city (kill any units belonging to it) if it gets captured (and does not start captured)
                }
                map.getMap()[i][j].getCity().setCapturing(false); //Change capturing parameter
                captureCity(map.getMap()[i][j].getCity(), map.getMap()[i][j].getUnit());
              }
            }
          }
        }
      }
    }
    
    private void emptyCity(City city) {
      for (int r = 0; r < map.getMap().length; r++) {
        for (int c = 0; c < map.getMap().length; c++) {
          if (map.getMap()[r][c].getUnit() != null) {
            if (map.getMap()[r][c].getUnit().getCity() == city) {
              map.getMap()[r][c].setUnit(null);
            }
          }
        }
      }
    }

}