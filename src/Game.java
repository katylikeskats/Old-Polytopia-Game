import java.util.ArrayList;

public class Game{
    int numPlayers;
    ArrayList<String> names;
    ArrayList<Player> players;

    Game(int numPlayers, ArrayList<String> names, ArrayList<Player> players){
        this.numPlayers = numPlayers;
        this.names = names;
        this.players = players;
    }

    public void turn(){
        for (int i = 0; i < players.size(); i++){
            players.get(i).turn();
        }
    }
}