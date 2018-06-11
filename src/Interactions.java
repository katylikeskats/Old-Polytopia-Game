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
        defender.setCurrHealth(defender.getCurrHealth() - attacker.getDamage(defender));
        attacker.setCurrHealth(attacker.getCurrHealth() - defender.getDamage(attacker));
    }

    public void die(Unit unit){
        City city = unit.getCity();
        city.setCurrUnits(city.getCurrUnits() - 1);
        map.getMap()[unit.getR()][unit.getC()] = null;
    }

    public boolean validateMove(Unit unit, int newR, int newC){
        return ((contains(unit.getCity().getPlayer().getTechnology(), map.getMap()[newR][newC].getTerrainName())) && (newR >= 0) && (newC >= 0) && (newR < map.getMap().length) && (newC < map.getMap().length) && (map.getMap()[newR][newC].getUnit() == null));
    }

    public void move (Unit unit, int newR, int newC){
        if (validateMove(unit, newR, newC)){
            if (map.getMap()[newR][newC].getTerrainName().equals("Water")){
                map.getMap()[unit.getR()][unit.getC()].setUnit(null);
                map.getMap()[newR][newC].setUnit(new Boat(newR, newC, unit));
                //trigger animation for movement
            } else {
                map.getMap()[unit.getR()][unit.getC()].setUnit(null);
                map.getMap()[newR][newC].setUnit(unit);
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

    public int displayOptions(int r, int c, boolean selected, boolean[][] mask){
        if (map.getMap()[r][c] != null){
            if (!mask[r][c]) {
                if (map.getMap()[r][c].containsUnit()){
                    return 1;
                } else if (map.getMap()[r][c].containsCity()){
                    return 2;
                } else if (map.getMap()[r][c].containsResource()){
                    return 3;
                } else {
                    return 5;
                }
            } /*else {
            return 4;
          }
          */
        }
        return 5;
    }


}