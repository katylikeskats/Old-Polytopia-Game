/**
 *
 */

import java.util.ArrayList;

//Removing units from cities once they die?

public class Interactions {
    private Space[][] tileMap;

    /**
     *
     * @param map
     */
    public Interactions(Map map){
        this.tileMap = map.getMap();
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
        tileMap[unit.getR()][unit.getC()].setUnit(null);
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
        if ((player.getTribe() != tileMap[newR][newC].getUnit().getTribe()) && (Math.abs(unit.getR() - newR) <= unit.getRange()) && (Math.abs(unit.getC() - newC) <= unit.getRange())) {
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
    public boolean validateMove(Unit unit, int newR, int newC, Player player, boolean[][] mask){
        if ((contains(player.getPractical(), tileMap[newR][newC].getTerrainName())) && (newR >= 0) && (newC >= 0) && (newR < tileMap.length) && (newC < tileMap.length) && (tileMap[newR][newC].getUnit() == null) && (Math.abs(unit.getR() - newR) <= unit.getMovement()) && (Math.abs(unit.getC() - newC) <= unit.getMovement()) && !(mask[newR][newC])){
            if ((tileMap[newR][newC].getTerrainName().equals("Water") && (!(unit instanceof Boat)))){
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     *
     * @param unit
     * @param newR
     * @param newC
     * @param mask
     */
    public void move (Unit unit, int newR, int newC, boolean[][] mask){
        if ((tileMap[newR][newC].getTerrainName().equals("Port")) && !(unit instanceof Boat)){ //Should be PORT instead of water
            tileMap[newR][newC].setUnit(new Boat(newR, newC, unit)); //Take into account prev unit's health?
            tileMap[unit.getR()][unit.getC()].setUnit(null);
        } else if ((unit instanceof Boat) && (tileMap[newR][newC].getTerrain() instanceof Water)) {
            tileMap[unit.getR()][unit.getC()].setUnit(null); //Move a boat normally (like a unit would on land) if it moves onto a water tile
            tileMap[newR][newC].setUnit(unit);
        } else if (unit instanceof Boat) {
            tileMap[unit.getR()][unit.getC()].setUnit(null); //If a boat moves onto a land tile, put the unit inside it onto the land tile
            tileMap[newR][newC].setUnit(((Boat) unit).getUnitContained()); //Cast a boat into the unit
        } else {
            tileMap[unit.getR()][unit.getC()].setUnit(null); //Non-boat unit moving onto land
            tileMap[newR][newC].setUnit(unit);
        }
        for (int i = (newR - 1); i < (newR + 2); i++) {
            for (int j = (newC - 1); j < (newC + 2); j++) {
                if ((i >= 0) && (j >= 0) && (i < tileMap.length) && (j < tileMap.length)) {
                    if (mask[i][j]) {
                        mask[i][j] = false; //Undo the mask when explored by a unit (in proximity)
                    }
                }
            }
        }
        tileMap[newR][newC].getUnit().setR(newR);
        tileMap[newR][newC].getUnit().setC(newC);
        if (tileMap[newR][newC].getCity() != null) {
            if (tileMap[newR][newC].getCity().getTribe() != unit.getTribe()) {
                tileMap[newR][newC].getCity().setCapturing(true); //Set a city to being captured if a unit of a different tribe has gone on it
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
        if (tileMap[r][c].getResource().getCity() != null) {
            if ((player.getCurrency() >= tileMap[r][c].getResource().getCost()) && (contains(player.getPractical(), tileMap[r][c].getResource().getClass().getSimpleName())) &&(tileMap[r][c].getResource().getCity().getTribe() == player.getTribe())){
                player.setCurrency(player.getCurrency() - tileMap[r][c].getResource().getCost());
                tileMap[r][c].getResource().getCity().increasePop(tileMap[r][c].getResource().getPopIncrease());
                //System.out.println(tileMap[r][c].getResource().getCity().getCurrPop());
                tileMap[r][c].setResource(null);
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
                if (tileMap[r][c].containsUnit()){
                    return 1; //If nothing is selected, then a unit is clicked on
                } else if (tileMap[r][c].containsCity()){
                    return 2; //If nothing is selected, then a city is clicked on
                } else if (tileMap[r][c].containsResource()){
                    return 3; //If nothing is selected, then a resource is clicked on
                } else if (tileMap[r][c].getTerrain() instanceof Water) {
                    return 6;
                } else {
                    return 5; //If a blank spot is clicked on
                }
            } else {
                return 5; //If clouds are clicked on
            }
        } else {
            if ((!((r == r2) && (c == c2))) && (tileMap[r][c].containsUnit())) { //If, after a unit is selected, another unit is selected that is not itself
                return 4;
            } else if (tileMap[r][c].containsUnit()) { //If, after a unit is selected, the same tile is selected again
                if (tileMap[r][c].getCity() != null) {
                    return 2; //Return that the city under the unit is to be selected if applicable
                } else if (tileMap[r][c].getResource() != null) {
                    return 3; //Return that the resource under the unit is to be selected if applicable
                } else {
                    return 5; //If nothing is under the unit that is selected twice, unselect it
                }
            } else { //If, after a unit is selected, an empty tile, a tile with a resource, or a tile with a city is selected (no unit)
                return 4; //If a blank spot or spot with a resource is clicked on (try to move on it) IF IT CANT MOVE THERE THE UNIT WILL BE UNSELECTED
            }
        }
    }


    public void trainUnit(Player player, String unitName, int r, int c) {
        if ((tileMap[r][c].getUnit() == null) && (player.getTribe() == tileMap[r][c].getCity().getTribe()) && (tileMap[r][c].getCity().getCurrUnits() != tileMap[r][c].getCity().getMaxUnits())) { //Can only create a unit if there's no unit on the city currently and the player training the unit owns the city and the city isn't full
            if (unitName == "Warrior") {
                if (player.getCurrency() >= Warrior.getCost()) {
                    player.setCurrency(player.getCurrency() - Warrior.getCost());
                    tileMap[r][c].getCity().setCurrUnits(tileMap[r][c].getCity().getCurrUnits() + 1);
                    tileMap[r][c].setUnit(new Warrior(r, c, player.getTribe(), tileMap[r][c].getCity()));
                }
            } else if (unitName == "Swordsperson") {
                if (player.getCurrency() >= Swordsperson.getCost()) {
                    player.setCurrency(player.getCurrency() - Swordsperson.getCost());
                    tileMap[r][c].getCity().setCurrUnits(tileMap[r][c].getCity().getCurrUnits() + 1);
                    tileMap[r][c].setUnit(new Swordsperson(r, c, player.getTribe(), tileMap[r][c].getCity()));
                }
            } else if (unitName == "Rider") {
                if (player.getCurrency() >= Rider.getCost()) {
                    player.setCurrency(player.getCurrency() - Rider.getCost());
                    tileMap[r][c].getCity().setCurrUnits(tileMap[r][c].getCity().getCurrUnits() + 1);
                    tileMap[r][c].setUnit(new Rider(r, c, player.getTribe(), tileMap[r][c].getCity()));
                }
            } else if (unitName == "Knight") {
                if (player.getCurrency() >= Knight.getCost()) {
                    player.setCurrency(player.getCurrency() - Knight.getCost());
                    tileMap[r][c].getCity().setCurrUnits(tileMap[r][c].getCity().getCurrUnits() + 1);
                    tileMap[r][c].setUnit(new Knight(r, c, player.getTribe(), tileMap[r][c].getCity()));
                }
            } else if (unitName == "Archer") {
                if (player.getCurrency() >= Archer.getCost()) {
                    player.setCurrency(player.getCurrency() - Archer.getCost());
                    tileMap[r][c].getCity().setCurrUnits(tileMap[r][c].getCity().getCurrUnits() + 1);
                    tileMap[r][c].setUnit(new Archer(r, c, player.getTribe(), tileMap[r][c].getCity()));
                }
            } else if (unitName == "Catapult") {
                if (player.getCurrency() >= Catapult.getCost()) {
                    player.setCurrency(player.getCurrency() - Catapult.getCost());
                    tileMap[r][c].getCity().setCurrUnits(tileMap[r][c].getCity().getCurrUnits() + 1);
                    tileMap[r][c].setUnit(new Catapult(r, c, player.getTribe(), tileMap[r][c].getCity()));
                }
            } else if (unitName == "Defender") {
                if (player.getCurrency() >= Defender.getCost()) {
                    player.setCurrency(player.getCurrency() - Defender.getCost());
                    tileMap[r][c].getCity().setCurrUnits(tileMap[r][c].getCity().getCurrUnits() + 1);
                    tileMap[r][c].setUnit(new Defender(r, c, player.getTribe(), tileMap[r][c].getCity()));
                }
            } else if (unitName == "Mindbender") {
                if (player.getCurrency() >= Mindbender.getCost()) {
                    player.setCurrency(player.getCurrency() - Mindbender.getCost());
                    tileMap[r][c].getCity().setCurrUnits(tileMap[r][c].getCity().getCurrUnits() + 1);
                    tileMap[r][c].setUnit(new Mindbender(r, c, player.getTribe(), tileMap[r][c].getCity()));
                }
            } else if (unitName == "Ninja"){
                if (player.getCurrency() >= Ninja.getCost()) {
                    player.setCurrency(player.getCurrency() - Ninja.getCost());
                    tileMap[r][c].getCity().setCurrUnits(tileMap[r][c].getCity().getCurrUnits() + 1);
                    tileMap[r][c].setUnit(new Ninja(r, c, player.getTribe(), tileMap[r][c].getCity()));
                }
            }
        }


    }

    //At the start of each player's turn, if the player's unit is on a city that is marked as being captured, capture the city
    public void cityCapturingCheck(Player player) {
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap.length; j++) {
                if (tileMap[i][j].getCity() != null) {
                    if ((tileMap[i][j].getCity().getCapturing()) && (tileMap[i][j].getUnit() != null)) {
                        if ((tileMap[i][j].getUnit().getTribe() != tileMap[i][j].getCity().getTribe()) && (tileMap[i][j].getUnit().getTribe() == player.getTribe())) {
                            if (tileMap[i][j].getCity().getTribe() != 5) {
                                emptyCity(tileMap[i][j].getCity()); //Empty the city (kill any units belonging to it) if it gets captured (and does not start captured)
                            }
                            tileMap[i][j].getCity().setCapturing(false); //Change capturing parameter
                            captureCity(tileMap[i][j].getCity(), tileMap[i][j].getUnit(), player);
                        }
                    }
                }
            }
        }
    }


    //CAPTURE CITY METHOD
    //NEED TO: Change the unit capturing it's city to the city that got captured, set the tribe ofthe city to the same as the player whose unit captured it
    public void captureCity(City city, Unit unit, Player player) {
        city.setTribe(unit.getTribe()); //Set the tribe of the city to that of the unit capturing it
        unit.getCity().setCurrUnits(unit.getCity().getCurrUnits() - 1); //Decrease number of units in the city that the unit capturing the new city was from
        unit.setCity(city); //Set the unit capturing the city's city to the city being captured
        unit.getCity().setCurrUnits(1);
        player.increaseCosts();
        player.addCity(city);
    }

    public void resetMove(){
        for (int i = 0; i < tileMap.length; i++){
            for (int j = 0; j < tileMap.length; j++){
                if (tileMap[i][j].getUnit() != null){
                    tileMap[i][j].getUnit().setMoved(false);
                }
            }
        }
    }

    private void emptyCity(City city) {
        for (int r = 0; r < tileMap.length; r++) {
            for (int c = 0; c < tileMap.length; c++) {
                if (tileMap[r][c].getUnit() != null) {
                    if (tileMap[r][c].getUnit().getCity() == city) {
                        tileMap[r][c].setUnit(null);
                    }
                }
            }
        }
    }

    public boolean portCheck(int row, int column) {
        if (withinCity(row, column)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean withinCity(int row, int column) {
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap.length; j++) {
                if (tileMap[i][j].getCity() != null) {
                    if ((Math.abs(row - i) < 2) && (Math.abs(column - j) < 2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void makePort(int row, int column,Player player) {
        if ((Utilities.contains(player.getTechnology(), "Sailing")) && (player.getCurrency() >= 10)) {
            player.setCurrency(player.getCurrency() - 10);
            player.setInGameBonus(player.getInGameBonus()+1);
            tileMap[row][column].setTerrain(new Port(row, column));

        }
    }

    public Space[][] getTileMap(){
        return this.tileMap;
    }

}