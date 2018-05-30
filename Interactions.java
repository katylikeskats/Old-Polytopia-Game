import java.util.ArrayList;

public class Interactions {
    protected Space[][] map;
    Interactions(Space[][] map){
        this.map = map;
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
        return ((contains(unit.getCity().getPlayer().getTechnology(), map[newR][newC].getTerrainName())) && (newR < map.length) && (newC < map.length));
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
        if ((player.getCurrency() >= map[r][c].getTerrain().getItem().getCost()) && (contains(player.getTechnology(), map[r][c].getTerrain().getItem().getClass().getSimpleName())) &&(map[r][c].getTerrain().getItem().getCity().getTribe().equals(player.getTribe()))){
            player.setCurrency(player.getCurrency() - map[r][c].getTerrain().getItem().getCost());
            map[r][c].getTerrain().getItem().getCity().increasePop(map[r][c].getTerrain().getItem().getPopIncrease());
            map[r][c].getTerrain().setItem(null);
        }
    }

    public void changeTribe(Mindbender mindbender, Unit unit){
        unit.setTribe(mindbender.getTribe());
    }


}
