package co.icesi.edu.model;

import co.icesi.edu.structures.PriorityQueue;
import co.icesi.edu.structures.Queue;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Deck deck;
    private PriorityQueue<Player> playerQueue;
    private boolean gameOver;

    public GameController() throws Exception {
        deck = new Deck();
        playerQueue = new PriorityQueue<>();
        gameOver = false;
    }

    public void startGame(List<String> playerNames) {
        // Lista para mantener a los jugadores después de crearlos
        List<Player> players = new ArrayList<>();

        // Crear objetos Player y añadirlos a la cola de prioridad y a la lista de jugadores
        int priority = 1;
        for (String name : playerNames) {
            Player player = new Player(name);
            playerQueue.enqueue(player, priority);
            players.add(player);
            priority++;
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


    //---------------------------------------------------------------------------------------

    // Método para que un jugador juegue una carta
    public boolean playCard(int cardIndex) {
        boolean flag = false;

        Player currentPlayer = playerQueue.peek();
        System.out.println("YOOOOOOOOOOO"+ currentPlayer());
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
            }
            flag = true;
            nextTurn();
        }
        return flag;
    }

    // Método para manejar el efecto de las cartas especiales
    private void handleSpecialCardEffect(Card card) {
        // Implementación de efectos basada en el tipo de carta especial
        switch (card.getSpecialType()) {
            case DRAW_TWO:
                drawCard(2);
                nextTurn();
                break;
            case REVERSE:
                // Implementación específica
                break;
            case SKIP:
                nextTurn();
                break;
            case CHANGE:
                // Implementación específica
                break;
        }
    }

    // Método para pasar al siguiente turno
    public void nextTurn() {
        // Extrae al jugador actual de la cola de prioridad.
        Player currentPlayer = playerQueue.dequeue();

        // Calcula la nueva prioridad. Podría ser el tamaño actual de la cola + 1,
        // asegurando que el jugador vuelve al final de la cola.
        int newPriority = playerQueue.size() + 1;

        // Vuelve a encolar al jugador con su nueva prioridad, colocándolo al final de la cola.
        playerQueue.enqueue(currentPlayer, newPriority);
    }


    // Método para robar una carta del mazo
    public void drawCard(int numberCards) {
        Player currentPlayer = playerQueue.peek();
        for (int i = 0; i < numberCards; i++) {
            // Extraer una carta del mazo y añadirla a la mano del jugador actual
            String drawnCardId = deck.getDiscardDeck().pop();
            currentPlayer.addCardToHand(drawnCardId);
        }
        nextTurn();
    }

    // Método para verificar el fin del juego
    private boolean checkGameOver() {
        // Obtener al jugador actual de la cola de prioridad
        Player currentPlayer = playerQueue.peek();

        // Verificar si la mano del jugador actual está vacía
        if (currentPlayer.getHand().isEmpty()) {
            // Si la mano del jugador está vacía, el jugador ha ganado
            gameOver = true;
        }

        return false; // El juego no ha terminado aún
    }

}


/*package co.icesi.edu.model;

import co.icesi.edu.structures.HashTable;
import co.icesi.edu.structures.PriorityQueue;
import co.icesi.edu.structures.Queue;
import co.icesi.edu.structures.Stack;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Deck deck;
    private List<Player> players;
    private Queue<Player> turnOrder;


    private Card currentCard;
    private boolean isReverse = false;

    public GameController(List<String> playersNames, int numPlayers) {
        deck = new Deck(cardReferenceTable);
        players = new ArrayList<>();
        for (String name : playersNames) {
            players.add(new Player(name, cardReferenceTable));
        }
        turnOrder = new Queue<>();
        distributeCards(numCards);
        initializeGame();
    }

    private void distributeCards(int numCards) {
        for (Player player : players) {
            for (int i = 0; i < numCards; i++) {
                player.addCardToHand(deck.drawCard());
            }
        }
        Card initialCard = deck.drawCard();
        deck.discardCard(initialCard);


        currentCard = initialCard;
    }


    private void initializeGame() {
        Card initialCard;
        do {
            initialCard = deck.drawCard();
        } while (initialCard.getSpecialType() != Card.SpecialType.NONE);

        deck.discardCard(initialCard);
    }

    public boolean playCard(Player player, int cardIndex, Card.Color chosenColor) {
        if (cardIndex < 0 || cardIndex >= player.getHandSize()) {
            System.out.println("Índice de carta inválido.");
            return false;
        }

        Card cardToPlay = player.getHand().get(cardIndex);
        if (!isPlayable(cardToPlay)) {
            System.out.println("La carta seleccionada no se puede jugar.");
            return false;
        }

        applyCardEffect(cardToPlay, chosenColor);

        player.removeCardFromHand(cardToPlay);
        deck.discardCard(cardToPlay);

        if (player.getHandSize() == 0) {
            System.out.println(player.getName() + " ha ganado el juego!");
            return true;
        }

        updateTurnOrder();
        return true;
    }

    private boolean isPlayable(Card card) {
        return card.getColor() == currentCard.getColor()  || card.getSpecialType() != currentCard.getSpecialType() || card.getNumber() == currentCard.getNumber();
    }
    private void applyCardEffect(Card card, Card.Color chosenColor) {
        switch (card.getSpecialType()) {
            case DRAW_TWO:
                Player nextPlayer = getNextPlayer();
                drawCards(nextPlayer, 2);
                break;
            case REVERSE:
                isReverse = !isReverse;
                break;
            case SKIP:
                getNextPlayer();
                break;
            case CHANGE:
                //currentCard.setColor();
                break;
        }
    }

    private void drawCards(Player player, int count) {
        for (int i = 0; i < count; i++) {
            player.addCardToHand(deck.drawCard());
        }
    }


    private void updateTurnOrder() {
        if (!isReverse) {
            Player player = turnOrder.dequeue();
            turnOrder.enqueue(player);
        } else {
            Queue<Player> tempQueue = new Queue<>();
            while (!turnOrder.isEmpty()) {
                tempQueue.enqueue(turnOrder.dequeue());
            }
            while (!tempQueue.isEmpty()) {
                turnOrder.enqueue(tempQueue.dequeue());
            }
        }
    }

    private Player getNextPlayer() {
        if (isReverse) {
            Queue<Player> tempQueue = new Queue<>();
            while (!turnOrder.isEmpty()) {
                tempQueue.enqueue(turnOrder.dequeue());
            }
            Player player = tempQueue.dequeue();
            while (!tempQueue.isEmpty()) {
                turnOrder.enqueue(tempQueue.dequeue());
            }
            return player;
        } else {
            return turnOrder.peek();
        }
    }

    public boolean isGameOver() {
        for (Player player : players) {
            if (player.getHandSize() == 0) {
                return true;
            }
        }
        return false;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Card getTopDiscardCard() {
        return deck.topDiscardCard();
    }

    public void playerDrawCard(Player player) {
        Card card = deck.drawCard();
        if (card != null) {
            player.addCardToHand(card);
        } else {
            System.out.println("El mazo de robar está vacío, reorganizando el mazo de descarte.");
            deck.refillDrawPileFromDiscardPile();
            card = deck.drawCard();
            if (card != null) {
                player.addCardToHand(card);
            } else {
                System.out.println("No fue posible robar una carta.");
            }
        }
    }

}

 */
