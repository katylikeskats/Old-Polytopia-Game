public class Boat extends Unit {
    Unit unit;
    Boat (int r, int c, Unit unit, String tribe){
        super (r, c, 2, 3, unit.getCurrHealth(), 1, 2, unit.getTribe());
        this.unit = unit;
    }

    @Override
    public void setCurrHealth(int health) {
        super.setCurrHealth(health);
        unit.setCurrHealth(health);
    }

    @Override
    public void levelUp() {
        this.setAttack(this.getAttack()+5);
    }
}
