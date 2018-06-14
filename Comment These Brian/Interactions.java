/**
 *
 */

import java.util.ArrayList;

//Removing units from cities once they die?

public class Interactions {
    private Map map;

    /**
     *
     * @param map
     */
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

    /**
     *
     * @param unit
     */
    private void die(Unit unit){
        City city = unit.getCity();
        city.setCurrUnits(city.getCurrUnits() - 1); //Decrease number of units of the city whose unit died
        map.getMap()[unit.getR()][unit.getC()].setUnit(null);
    }

    /**
     *
     * @param unit
     * @param newR
     * @param newC
     * @param player
     * @return
     */
    public boolean validateAttack(Unit unit, int newR, int newC, Player player) {
        if ((player.getTribe() != map.getMap()[newR][newC].getUnit().getTribe()) && (Math.abs(unit.getR() - newR) <= unit.getRange()) && (Math.abs(unit.getC() - newC) <= unit.getRange())) {
            //check to make sure that the player's tribe is different from the unit on newR, newC's tribe, and that they unit being attacked is in range
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param unit
     * @param newR
     * @param newC
     * @param player
     * @param mask
     * @return
     */
    //Have to check that they're not moving on a masked spot? (for like riders) DO THIS
    public boolean validateMove(Unit unit, int newR, int newC, Player player, boolean[][] mask){
        return ((contains(player.getPractical(), map.getMap()[newR][newC].getTerrainName())) && (newR >= 0) && (newC >= 0) && (newR < map.getMap().length) && (newC < map.getMap().length) && (map.getMap()[newR][newC].getUnit() == null) && (Math.abs(unit.getR() - newR) <= unit.getMovement()) && (Math.abs(unit.getC() - newC) <= unit.getMovement()) && !(mask[newR][newC]));
    }

    /**
     *
     * @param unit
     * @param newR
     * @param newC
     * @param mask
     */
    //IMPORTANT - PORTS HAVE TO BE GRASS SINCE ANYONE CAN MOVE ON THEM
    public void move (Unit unit, int newR, int newC, boolean[][] mask){
        if ((map.getMap()[newR][newC].getTerrainName().equals("Water")) && !(unit instanceof Boat)){ //Should be PORT instead of water
            map.getMap()[newR][newC].setUnit(new Boat(newR, newC, unit)); //Take into account prev unit's health?
            map.getMap()[unit.getR()][unit.getC()].setUnit(null);
        } else if ((unit instanceof Boat) && (map.getMap()[newR][newC].getTerrain() instanceof Water)) {
            map.getMap()[unit.getR()][unit.getC()].setUnit(null); //Move a boat normally (like a unit would on land) if it moves onto a water tile
            map.getMap()[newR][newC].setUnit(unit);
        } else if (unit instanceof Boat) {
            map.getMap()[unit.getR()][unit.getC()].setUnit(null); //If a boat moves onto a land tile, put the unit inside it onto the land tile
            map.getMap()[newR][newC].setUnit(((Boat)(unit)).getUnitContained()); //Cast a boat into the unit
        } else {
            map.getMap()[unit.getR()][unit.getC()].setUnit(null); //Non-boat unit moving onto land
            map.getMap()[newR][newC].setUnit(unit);
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
        unit.setR(newR); //Change the position of the unit in the unit object
        unit.setC(newC);
        if (map.getMap()[newR][newC].getCity() != null) {
            if (map.getMap()[newR][newC].getCity().getTribe() != unit.getTribe()) {
                map.getMap()[newR][newC].getCity().setCapturing(true); //Set a city to being captured if a unit of a different tribe has gone on it
            }
        }
    }

    /**
     *
     * @param list
     * @param item
     * @return
     */
    public boolean contains(ArrayList<String> list, String item){
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).equals(item)){
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param player
     * @param r
     * @param c
     * @return
     */
    //ISSUE - After levelling up, it doesn't give the city population anymore???
    public boolean harvestItem (Player player, int r, int c){
        if (map.getMap()[r][c].getResource().getCity() != null) {
            if ((player.getCurrency() >= map.getMap()[r][c].getResource().getCost()) && (contains(player.getPractical(), map.getMap()[r][c].getResource().getClass().getSimpleName())) &&(map.getMap()[r][c].getResource().getCity().getTribe() == player.getTribe())){
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

    /**
     *
     * @param mindbender
     * @param unit
     */
    public void changeTribe(Mindbender mindbender, Unit unit){
        unit.setTribe(mindbender.getTribe());
    }

    /**
     *
     * @param r
     * @param c
     * @param r2
     * @param c2
     * @param unitSelected
     * @param mask
     * @return
     */
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

    /**
     *
     * @param player
     * @param unitName
     * @param r
     * @param c
     */
    public void trainUnit(Player player, String unitName, int r, int c) {
        if ((map.getMap()[r][c].getUnit() == null) && (player.getTribe() == map.getMap()[r][c].getCity().getTribe()) && (map.getMap()[r][c].getCity().getCurrUnits() != map.getMap()[r][c].getCity().getMaxUnits())) { //Can only create a unit if there's no unit on the city currently and the player training the unit owns the city and the city isn't full
            if (unitName == "Warrior") {
                if (player.getCurrency() >= Warrior.getCost()) {
                    player.setCurrency(player.getCurrency() - Warrior.getCost());
                    map.getMap()[r][c].getCity().setCurrUnits(map.getMap()[r][c].getCity().getCurrUnits() + 1);
                    map.getMap()[r][c].setUnit(new Warrior(r, c, player.getTribe(), map.getMap()[r][c].getCity()));
                }
            } else if (unitName == "Swordsperson") {
                if (player.getCurrency() >= Swordsperson.getCost()) {
                    player.setCurrency(player.getCurrency() - Swordsperson.getCost());
                    map.getMap()[r][c].getCity().setCurrUnits(map.getMap()[r][c].getCity().getCurrUnits() + 1);
                    map.getMap()[r][c].setUnit(new Swordsperson(r, c, player.getTribe(), map.getMap()[r][c].getCity()));
                }
            } else if (unitName == "Rider") {
                if (player.getCurrency() >= Rider.getCost()) {
                    player.setCurrency(player.getCurrency() - Rider.getCost());
                    map.getMap()[r][c].getCity().setCurrUnits(map.getMap()[r][c].getCity().getCurrUnits() + 1);
                    map.getMap()[r][c].setUnit(new Rider(r, c, player.getTribe(), map.getMap()[r][c].getCity()));
                }
            } else if (unitName == "Knight") {
                if (player.getCurrency() >= Knight.getCost()) {
                    player.setCurrency(player.getCurrency() - Knight.getCost());
                    map.getMap()[r][c].getCity().setCurrUnits(map.getMap()[r][c].getCity().getCurrUnits() + 1);
                    map.getMap()[r][c].setUnit(new Knight(r, c, player.getTribe(), map.getMap()[r][c].getCity()));
                }
            } else if (unitName == "Archer") {
                if (player.getCurrency() >= Archer.getCost()) {
                    player.setCurrency(player.getCurrency() - Archer.getCost());
                    map.getMap()[r][c].getCity().setCurrUnits(map.getMap()[r][c].getCity().getCurrUnits() + 1);
                    map.getMap()[r][c].setUnit(new Archer(r, c, player.getTribe(), map.getMap()[r][c].getCity()));
                }
            } else if (unitName == "Catapult") {
                if (player.getCurrency() >= Catapult.getCost()) {
                    player.setCurrency(player.getCurrency() - Catapult.getCost());
                    map.getMap()[r][c].getCity().setCurrUnits(map.getMap()[r][c].getCity().getCurrUnits() + 1);
                    map.getMap()[r][c].setUnit(new Catapult(r, c, player.getTribe(), map.getMap()[r][c].getCity()));
                }
            } else if (unitName == "Defender") {
                if (player.getCurrency() >= Defender.getCost()) {
                    player.setCurrency(player.getCurrency() - Defender.getCost());
                    map.getMap()[r][c].getCity().setCurrUnits(map.getMap()[r][c].getCity().getCurrUnits() + 1);
                    map.getMap()[r][c].setUnit(new Defender(r, c, player.getTribe(), map.getMap()[r][c].getCity()));
                }
            } else if (unitName == "Mindbender") {
                if (player.getCurrency() >= Mindbender.getCost()) {
                    player.setCurrency(player.getCurrency() - Mindbender.getCost());
                    map.getMap()[r][c].getCity().setCurrUnits(map.getMap()[r][c].getCity().getCurrUnits() + 1);
                    map.getMap()[r][c].setUnit(new Mindbender(r, c, player.getTribe(), map.getMap()[r][c].getCity()));
                }
            } else if (unitName == "Ninja"){
                if (player.getCurrency() >= Ninja.getCost()) {
                    player.setCurrency(player.getCurrency() - Ninja.getCost());
                    map.getMap()[r][c].getCity().setCurrUnits(map.getMap()[r][c].getCity().getCurrUnits() + 1);
                    map.getMap()[r][c].setUnit(new Ninja(r, c, player.getTribe(), map.getMap()[r][c].getCity()));
                }
            }
        }


    }

    /**
     *
     * @param player
     */
    //At the start of each player's turn, if the player's unit is on a city that is marked as being captured, capture the city
    public void cityCapturingCheck(Player player) {
        for (int i = 0; i < map.getMap().length; i++) {
            for (int j = 0; j < map.getMap().length; j++) {
                if (map.getMap()[i][j].getCity() != null) {
                    if ((map.getMap()[i][j].getCity().getCapturing()) && (map.getMap()[i][j].getUnit() != null)) {
                        if ((map.getMap()[i][j].getUnit().getTribe() != map.getMap()[i][j].getCity().getTribe()) && (map.getMap()[i][j].getUnit().getTribe() == player.getTribe())) {
                            map.getMap()[i][j].getCity().setCapturing(false); //Change capturing parameter
                            captureCity(map.getMap()[i][j].getCity(), map.getMap()[i][j].getUnit());
                            player.setNumCities(player.getNumCities()+1);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param city
     * @param unit
     */
    //CAPTURE CITY METHOD
    //NEED TO: Change the unit capturing it's city to the city that got captured, set the tribe ofthe city to the same as the player whose unit captured it
    public void captureCity(City city, Unit unit) {
        city.setTribe(unit.getTribe()); //Set the tribe of the city to that of the unit capturing it
        unit.getCity().setCurrUnits(unit.getCity().getCurrUnits() - 1); //Decrease number of units in the city that the unit capturing the new city was from
        unit.setCity(city); //Set the unit capturing the city's city to the city being captured
        unit.getCity().setCurrUnits(1);
    }

    /**
     *
     */
    public void resetMove(){
        for (int i = 0; i < map.getMap().length; i++){
            for (int j = 0; j < map.getMap().length; j++){
                if (map.getMap()[i][j].getUnit() != null){
                    map.getMap()[i][j].getUnit().setMoved(false);
                }
            }
        }
    }

}