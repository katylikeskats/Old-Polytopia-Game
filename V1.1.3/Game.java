import java.util.ArrayList;

public class Game{
    int numPlayers;
    ArrayList<String> names;
    ArrayList<Player> players;
    Map map;
    private boolean gameEnd; //Initialized to false

    Game(int numPlayers, ArrayList<String> names, ArrayList<Player> players, Map map){
        this.numPlayers = numPlayers;
        this.names = names;
        this.players = players;
        this.map = map;
    }

    public void turn(){
      do {
        for (int i = 0; i < players.size(); i++){
            new GameFrame(map, players.get(i).getMask(), players.get(i));
            //System.out.println(players.get(i).getCurrency());
            players.get(i).turnCurrencyIncrease();
            //System.out.println(players.get(i).getCurrency());
            players.get(i).addTechnology("Animal"); //Temp for testing
            players.get(i).addTechnology("Fruit");
            players.get(i).addTechnology("Fish");
            players.get(i).addTechnology("Forest");
            players.get(i).addTechnology("Crop");
            players.get(i).addTechnology("Whale");
            players.get(i).addTechnology("Grass"); //Needed??
            players.get(i).turn();
        }
      } while (!gameEnd);
    }
}