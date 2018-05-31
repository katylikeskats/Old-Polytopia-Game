import java.util.ArrayList;
import java.awt.Graphics;

public class Interactions {
    protected Space[][] map;
    private GameGraphics gameGraphics;

    public Interactions(Space[][] map){
        this.map = map;
        this.gameGraphics = gameGraphics; //DETERMINE IF GRAPHICS IS GONNA BE STATIC OR NOT
    }

    /**
     *
     * @param attacker
     * @param defender
     */
    public void attack(Unit attacker, Unit defender){
        defender.setCurrHealth(defender.getCurrHealth() - attacker.getDamage(defender));
        attacker.setCurrHealth(attacker.getCurrHealth() - defender.getDamage(attacker));
    }

    public void die(Unit unit){
        City city = unit.getCity();
        city.setCurrUnits(city.getCurrUnits() - 1);
        map[unit.getR()][unit.getC()] = null;
    }

    public boolean validateMove(Unit unit, int newR, int newC){
        return ((contains(unit.getCity().getPlayer().getTechnology(), map[newR][newC].getTerrainName())) && (newR >= 0) && (newC >= 0) && (newR < map.length) && (newC < map.length) && (map[newR][newC].getUnit() == null));
    }

    public void move (Unit unit, int newR, int newC){
        if (validateMove(unit, newR, newC)){
            if (map[newR][newC].getTerrainName().equals("Water")){
                map[unit.getR()][unit.getC()].setUnit(null);
                map[newR][newC].setUnit(new Boat(newR, newC, unit));
                //trigger animation for movement
            } else {
                map[unit.getR()][unit.getC()].setUnit(null);
                map[newR][newC].setUnit(unit);
                //trigger animation for movement
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

    public void harvestItem (Player player, int r, int c){
        if ((player.getCurrency() >= map[r][c].getResource().getCost()) && (contains(player.getTechnology(), map[r][c].getResource().getClass().getSimpleName())) &&(map[r][c].getResource().getCity().getTribe() == player.getTribe())){
            player.setCurrency(player.getCurrency() - map[r][c].getResource().getCost());
            map[r][c].getResource().getCity().increasePop(map[r][c].getResource().getPopIncrease());
            map[r][c].setResource(null);
        }
    }

    public void changeTribe(Mindbender mindbender, Unit unit){
        unit.setTribe(mindbender.getTribe());
    }

    public void displayOptions(int r, int c){
        if (!map[r][c].isEmpty()){
            if (map[r][c].containsCity()){
                GameMapFrame.display
            }
            if (map[r][c].containsResource()){

            }
            if (map[r][c].containsUnit()){

            }
        }
    }
}
