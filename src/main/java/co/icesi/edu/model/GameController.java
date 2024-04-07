package co.icesi.edu.model;

import co.icesi.edu.structures.PriorityQueue;
import co.icesi.edu.structures.Queue;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Deck deck;
    private PriorityQueue<Player> playerQueue;
    private boolean gameOver;

    private boolean activeSpecialcard;

    public GameController() throws Exception {
        deck = new Deck();
        playerQueue = new PriorityQueue<>();
        gameOver = false;
        activeSpecialcard = false;
    }

    public void startGame(List<String> playerNames) {
        // Lista para mantener a los jugadores después de crearlos
        List<Player> players = new ArrayList<>();

        // Crear objetos Player y añadirlos a la cola de prioridad y a la lista de jugadores
        int priority = 1; // Comenzar con la prioridad más baja
        for (String name : playerNames) {
            Player player = new Player(name);
            players.add(player);
            playerQueue.enqueue(player, priority);
            priority++; // Incrementar la prioridad para el próximo jugador
        }


        // Preparar las cartas para repartir
        Queue<String> cardsToDeal = new Queue<>();
        int totalCardsToDeal = players.size() * 7;
        for (int i = 0; i < totalCardsToDeal; i++) {
            String cardId = deck.getDiscardDeck().pop();
            cardsToDeal.enqueue(cardId);
        }

        // Repartir las cartas a los jugadores
        while (!cardsToDeal.isEmpty()) {
            for (Player player : players) {
                if (!cardsToDeal.isEmpty()) {
                    String cardId = cardsToDeal.dequeue();
                    player.addCardToHand(cardId);
                }
            }
        }

        // Recorrer el mazo de descarte hasta encontrar una carta con especialidad NONE
        while (!deck.getDiscardDeck().isEmpty()) {
            String cardId = deck.getDiscardDeck().peek(); // Obtener el ID de la carta sin eliminarla
            Card card = deck.getCardTable().get(cardId);

            if (card.getSpecialType() == Card.SpecialType.NONE) {
                // Si la carta tiene especialidad NONE, añadirla al mazo de juego y salir del bucle
                deck.getPlayDeck().push(deck.getDiscardDeck().pop());
                break;
            } else {
                // Si la carta no tiene especialidad NONE, añadirla al mazo de juego y continuar recorriendo
                deck.getPlayDeck().push(deck.getDiscardDeck().pop());
            }
        }

    }

    //---------------------------------------------------------------------------------------

    public boolean isGameOver() {
        return gameOver;
    }

    public String currentPlayer(){
        return playerQueue.peek().getName();
    }

    public int handSizePlayer(){
        return playerQueue.peek().getHand().size();
    }

    public String currentCard() {
        String x = deck.getPlayDeck().peek();
        return deck.cardForId(x);
    }

    public String currentPlayerCardList() {
        List<String> list = playerQueue.peek().getHand();

        String result = "";

        for (int i = 0; i < list.size(); i++) {
            result +=  i+1 + ".  " + deck.cardForId(list.get(i)) + "\n";
        }

        return result;
    }

    public boolean isActiveSpecialcard() {
        String topCardId = deck.getPlayDeck().peek();
        Card topCard = deck.getCardTable().get(topCardId);

        boolean flag = false;

        if (topCard.getSpecialType() == Card.SpecialType.DRAW_TWO || topCard.getSpecialType() == Card.SpecialType.SKIP) {
            if (activeSpecialcard == true) {
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    //---------------------------------------------------------------------------------------

    // Método para que un jugador juegue una carta
    public boolean playCard(int cardIndex) {
        boolean flag = false;

        Player currentPlayer = playerQueue.peek();
        String cardId = currentPlayer.getHand().get(cardIndex);
        Card playedCard = deck.getCardTable().get(cardId);

        // Obtiene la carta en la cima de la pila de juego para comparar
        String topCardId = deck.getPlayDeck().peek();
        Card topCard = deck.getCardTable().get(topCardId);

        // Comprueba si la carta es jugable
        if (topCard.getSpecialType() == Card.SpecialType.NONE) {
            if (playedCard.getColor() == topCard.getColor() || playedCard.getNumber() == topCard.getNumber()) {
                // Mueve la carta al montón de descarte
                currentPlayer.removeCardFromHand(cardId);
                deck.getPlayDeck().push(cardId);

                // Verifica si el juego ha terminado
                gameOver = checkGameOver();
                flag = true;
            }
        } else if (topCard.getSpecialType() != Card.SpecialType.CHANGE) {
            if(playedCard.getColor() == topCard.getColor() ) {
                // Mueve la carta al montón de descarte
                currentPlayer.removeCardFromHand(cardId);
                deck.getPlayDeck().push(cardId);

                // Verifica si el juego ha terminado
                gameOver = checkGameOver();
                flag = true;
                activeSpecialcard = true;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    // Método para manejar el efecto de las cartas especiales
    public void handleSpecialCardEffect() {
        // Obtiene la carta en la cima de la pila de juego para comparar
        String topCardId = deck.getPlayDeck().peek();
        Card topCard = deck.getCardTable().get(topCardId);

        // Implementación de efectos basada en el tipo de carta especial
        switch (topCard.getSpecialType()) {
            case DRAW_TWO:
                drawCard(2);
                nextTurn();
                break;
            case SKIP:
                nextTurn();
                break;
            case REVERSE:
                nextTurnNegative();
                break;
            case CHANGE:
        }
        activeSpecialcard = false;
    }

    // Método para pasar al siguiente turno
    public void nextTurn() {


        int size = playerQueue.size();

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Player nextPlayer = playerQueue.dequeue();
            players.add(nextPlayer);
        }

        for (Player nextPlayer : players) {
            playerQueue.enqueue(nextPlayer, 1+size);
        }

        // Extrae al jugador actual de la cola de prioridad.
        Player currentPlayer = playerQueue.dequeue();

        // Vuelve a encolar al jugador actual con prioridad 1, colocándolo al final de la cola.
        playerQueue.enqueue(currentPlayer, 1);
    }

    public void nextTurnNegative() {
        //
    }


    // Método para robar una carta del mazo
    public void drawCard(int numberCards) {
        Player currentPlayer = playerQueue.peek();
        for (int i = 0; i < numberCards; i++) {
            // Extraer una carta del mazo y añadirla a la mano del jugador actual
            String drawnCardId = deck.getDiscardDeck().pop();
            currentPlayer.addCardToHand(drawnCardId);
        }
    }

    // Método para verificar el fin del juego
    private boolean checkGameOver() {
        // Obtener al jugador actual de la cola de prioridad
        Player currentPlayer = playerQueue.peek();

        // Verificar si la mano del jugador actual está vacía
        if (currentPlayer.getHand().isEmpty()) {
            // Si la mano del jugador está vacía, el jugador ha ganado
            gameOver = true;
            return true;
        } else {
            return false;
        }
    }

}

