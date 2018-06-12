import java.util.ArrayList;

class GameTest {

    public static void main(String[] args) {

        ArrayList<Player> players = new ArrayList<Player>();
        ArrayList<String> playerNames = new ArrayList<String>();
        Map map = new Map(20, 4);
        Interactions interactions = new Interactions(map);
        Player tempPlayer;

        playerNames.add("Player 1");
        playerNames.add("Player 2");
        playerNames.add("Player 3");
        playerNames.add("Player 4");

        //Add the players, assigning them tribes (and thus capital cities), and assigning the interactions object to each
        for (int i = 0; i < map.getMapLength(); i++) {
            for (int j = 0; j < map.getMapLength(); j++) {
                if (map.getMap()[i][j].getCity() != null) {
                    if (map.getMap()[i][j].getCity().isCapital()) {
                        tempPlayer = new Player(map.getMap()[i][j].getCity(), map.getMap()[i][j].getCity().getTribe(), interactions, map.getMapLength());
                        tempPlayer.addTechnology("StartingTech");
                        players.add(tempPlayer);
                    }
                }
            }
        }

        Game game = new Game(players.size(), playerNames, players, map);

        game.turn();
    
    /*
    for (int i = 0; i < players.size(); i++) {
      System.out.println(playerNames.get(i));
    }
    */

    }

}