public class Mountain extends Terrain{
    Mountain(int r, int c){
        super(r, c, null);
        super.setDefenseMultiplier(1.5);
    }
}
