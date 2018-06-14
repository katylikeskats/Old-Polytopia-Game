import java.util.ArrayList;

public class Game{
    int numPlayers;
    ArrayList<String> names;
    ArrayList<Player> players;
    Map map;

    Game(int numPlayers, ArrayList<String> names, ArrayList<Player> players, Map map){
        this.numPlayers = numPlayers;
        this.names = names;
        this.players = players;
        this.map = map;
    }

    public void turn(){
      do {
        for (int i = 0; i < players.size(); i++){
          if ((i < players.size()) && !(checkGameEnd())) { //Check to ensure that the player still exists (since players get removed as they lose)
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
            removePlayersLost();
          }
        }
      } while (!checkGameEnd());
      System.out.println(getWinnerName() + " has won!"); //Sample (use the method though)
    }
    
    private boolean checkGameEnd() {
      boolean tribesLeft[] = new boolean[numPlayers];
      int numTribesLeft = 0;
      for (int i = 0; i < map.getMap().length; i++) {
        for (int j = 0; j < map.getMap().length; j++) {
          if (map.getMap()[i][j].getCity() != null) {
            if (!(tribesLeft[map.getMap()[i][j].getCity().getTribe()]) && (map.getMap()[i][j].getCity().isCapital())) {
              tribesLeft[map.getMap()[i][j].getCity().getTribe()] = true;
              numTribesLeft++;
            }
          }
        }
      }
      if (numTribesLeft == 1) {
        return true;
      } else {
        return false;
      }
    }
    
    private void removePlayersLost() {
      boolean tribesLeft[] = new boolean[numPlayers];
      for (int i = 0; i < map.getMap().length; i++) {
        for (int j = 0; j < map.getMap().length; j++) {
          if (map.getMap()[i][j].getCity() != null) {
            if (!(tribesLeft[map.getMap()[i][j].getCity().getTribe()]) && (map.getMap()[i][j].getCity().isCapital())) {
              tribesLeft[map.getMap()[i][j].getCity().getTribe()] = true;
            }
          }
        }
      }
      for (int i = 0; i < players.size(); i++) {
        if (i < players.size()) { //Check that the player still exists (since removing from list inside the loop)
          if (!tribesLeft[players.get(i).getTribe()]) {
            players.remove(i); //Remove a player from the game if they lost their capital city
          }
        }
      }
    }
    
    private String getWinnerName() {
        players.get(i).getName(); //Return the name of the only player left
    }
    
}
