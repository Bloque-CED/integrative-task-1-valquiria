package co.icesi.edu.structures;

import java.util.List;

public class PriorityQueues {
    private Queues<String> players;

    public PriorityQueues(List<String> namePlayers) {
        for (String namePlayer : namePlayers) {
            players.enqueue(namePlayer);
        }
    }

    //Para las veces que se salta algun jugador
    public void advanceInTheGame() {
        String firstPlayer = players.dequeue();
        players.enqueue(firstPlayer);
    }

    //Para mostrar el jugador al que le toca el turno, siempre ira de primero
    public String nextPlayer() {
        return players.peek();
    }

    //Para el inicio del juego, muestra el orden que se llevara (Hay que probarlo)
    public String showGameOrder() {
        String msg = "This is the order of the game: \n";
        Queues<String> listPlayers = players;
        int n = 0;

        while (!listPlayers.isEmpty()) {
            n++;
            msg += n + ". " + players.dequeue() + "\n";
        }
        return msg;
    }
}