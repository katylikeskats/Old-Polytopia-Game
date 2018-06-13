import java.util.ArrayList;

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
          map.getMap()[defender.getR()][defender.getC()].setUnit(null); //Just set defender/attacker to null? (will that work)
          attacker.setKillCount(attacker.getKillCount() + 1); //DO MOVEMENT AFTER ATTACKING
          if (attacker.getKillCount() == 3) {
            attacker.levelUp();
          }
        } else if (attacker.getCurrHealth() <= 0) {
          map.getMap()[attacker.getR()][attacker.getC()].setUnit(null);
          defender.setKillCount(defender.getKillCount() + 1);
          if (defender.getKillCount() == 3) {
            defender.levelUp();
          }
        }
    }

    public void die(Unit unit){
        City city = unit.getCity();
        city.setCurrUnits(city.getCurrUnits() - 1);
        map.getMap()[unit.getR()][unit.getC()] = null;
    }

    public boolean validateAttack(Unit unit, int newR, int newC, Player player) {
      if ((player.getTribe() != map.getMap()[newR][newC].getUnit().getTribe()) && (Math.abs(unit.getR() - newR) <= unit.getRange()) && (Math.abs(unit.getC() - newC) <= unit.getRange())) {
      //check to make sure that the player's tribe is different from the unit on newR, newC's tribe, and that they unit being attacked is in range
        return true;
      } else {
        return false;
      }
    }
    
    public boolean validateMove(Unit unit, int newR, int newC, Player player){
        return ((contains(player.getTechnology(), map.getMap()[newR][newC].getTerrainName())) && (newR >= 0) && (newC >= 0) && (newR < map.getMap().length) && (newC < map.getMap().length) && (map.getMap()[newR][newC].getUnit() == null) && (Math.abs(unit.getR() - newR) <= unit.getMovement()) && (Math.abs(unit.getC() - newC) <= unit.getMovement()));
    }

    //IMPORTANT - PORTS HAVE TO BE GRASS SINCE ANYONE CAN MOVE ON THEM
    public void move (Unit unit, int newR, int newC){
      if (map.getMap()[newR][newC].getTerrainName().equals("Water")){
        map.getMap()[newR][newC].setUnit(new Boat(newR, newC, unit)); //Take into account prev unit's health?
        map.getMap()[unit.getR()][unit.getC()].setUnit(null);
        //trigger animation for movement
      } else {
        map.getMap()[unit.getR()][unit.getC()].setUnit(null);
        map.getMap()[newR][newC].setUnit(unit);
        //trigger animation for movement
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

    public int displayOptions(int r, int c, int r2, int c2, boolean unitSelected, boolean[][] mask){
        if (!unitSelected){
            if (!mask[r][c]) {
              if (map.getMap()[r][c].containsUnit()){
                return 1; //If nothing is selected, then a unit is clicked on
              } else if (map.getMap()[r][c].containsCity()){
                return 2; //If nothing is selected, then a city is clicked on
              } else if (map.getMap()[r][c].containsResource()){
                return 3; //If nothing is selected, then a resource is clicked on
              } else {
                return 5; //If a blank spot is clicked on
              }
          } else {
            return 5; //If clouds are clicked on
          }
        } else {
          if ((!((r == r2) && (c == c2))) && (map.getMap()[r][c].containsUnit())) { //If, after a unit is selected, another unit is selected that is not itself
            return 4;
          } else if (map.getMap()[r][c].containsUnit()) { //If, after a unit is selected, the same tile is selected again
            if (map.getMap()[r][c].getCity() != null) {
              return 2; //Return that the city under the unit is to be selected if applicable
            } else if (map.getMap()[r][c].getResource() != null) {
              return 3; //Return that the resource under the unit is to be selected if applicable
            } else {
              return 5; //If nothing is under the unit that is selected twice, unselect it
            }
          } else { //If, after a unit is selected, an empty tile, a tile with a resource, or a tile with a city is selected (no unit)
            return 4; //If a blank spot or spot with a resource is clicked on (try to move on it) IF IT CANT MOVE THERE THE UNIT WILL BE UNSELECTED
          }
        }
    }
    
    public boolean trainUnit(Player player, String unitName, int r, int c) {
      if ((map.getMap()[r][c].getUnit() == null) && (player.getTribe() == map.getMap()[r][c].getCity().getTribe())) { //Can only create a unit if there's no unit on the city currently and the player training the unit owns the city
        if (unitName == "Warrior") {
          if (player.getCurrency() >= Warrior.getCost()) {
            player.setCurrency(player.getCurrency() - Warrior.getCost());
            map.getMap()[r][c].setUnit(new Warrior(r, c, player.getTribe(), map.getMap()[r][c].getCity()));
            return true;
          }
        }
      }
      
      return false;
      
    }


}