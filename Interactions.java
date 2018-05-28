public class Interactions {
    protected Terrain[][] terrMap;
    protected Unit[][] unitMap;
    Interactions(Space[][] map){
        this.map = map;
    }

    public void Attack(Unit attacker, Unit defender){
        defender.setCurrHealth(defender.getCurrHealth() - attacker.getDamage(defender));

    }
}
