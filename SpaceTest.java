public class SpaceTest {
    public static void main(String[] args){
        City city = new City (1, 1, true);
        Unit warrior = new Warrior (1, 1, "Oumanji", city);
        Grass grass = new Grass(1, 1);
        Space space =new Space(warrior, grass);
        System.out.println(space.getTerrainName());
        System.out.println(space.getUnitName());
    }
}
