package co.icesi.edu.model;

import co.icesi.edu.structures.PriorityQueue;
import co.icesi.edu.structures.Queue;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Deck deck;
    private PriorityQueue<Player> playerQueue;
    private boolean gameOver;
    private boolean invert;
    private Card.Color auxiliaryCard;
    private boolean activeSpecialcard;
    private boolean changeColor;
    private boolean changeColorController;
    private boolean takeCard;

    /**
     * <b>GameController Constructor</b>
     * Initializes the game controller, including the deck, player queue, and game state.
     * <b>pre:</b> None.
     * <b>post:</b> The game controller has been initialized.
     * @throws Exception if an error occurs during initialization
     */
    public GameController() throws Exception {
        deck = new Deck();
        playerQueue = new PriorityQueue<>();
        gameOver = false;
        activeSpecialcard = false;
        invert = false;
        auxiliaryCard = null;
        changeColor = false;
        changeColorController = false;
        takeCard = true;
    }


    //--------------------------------------------------------------------------------------------//
    //Getters and setters
    public boolean isGameOver() {
        return gameOver;
    }
    public boolean isChanged() {
        return changeColor;
    }
    public boolean isTakeCard() {
        return takeCard;
    }
    public void setTakeCard(boolean takeCard) {
        this.takeCard = takeCard;
    }

    //--------------------------------------------------------------------------------------------//

    /**
     * <b>startGame</b>
     * Starts the game by creating players, distributing cards, and preparing the play deck.
     * <b>pre:</b> A list of player names is provided.
     * <b>post:</b> The game is started with the specified players and initial game state.
     * @param playerNames a list of player names
     */
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


        // Preparar las cartas para repartir MEDIANTE UNA COLA
        Queue<String> cardsToDeal = new Queue<>();
        int totalCardsToDeal = players.size() * 7;
        for (int i = 0; i < totalCardsToDeal; i++) {
            //String cardId = deck.getDiscardDeck().pop();
            //cardsToDeal.enqueue(cardId);
            //Puede resumirse codigo
            cardsToDeal.enqueue(deck.getDiscardDeck().pop());
        }

        // Repartir las cartas a los jugadores
        while (!cardsToDeal.isEmpty()) {
            for (Player player : players) {
                if (!cardsToDeal.isEmpty()) {
                    //String cardId = cardsToDeal.dequeue();
                    //player.addCardToHand(cardId);
                    //Resumir codigo
                    player.addCardToHand(cardsToDeal.dequeue());
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

    /**
     * <b>currentPlayer</b>
     * Retrieves the name of the current player.
     * <b>pre:</b> None.
     * <b>post:</b> The name of the current player has been retrieved.
     * @return the name of the current player
     */
    public String currentPlayer(){
        return playerQueue.peek().getName();
    }

    /**
     * <b>handSizePlayer</b>
     * Retrieves the hand size of the current player.
     * <b>pre:</b> None.
     * <b>post:</b> The hand size of the current player has been retrieved.
     * @return the hand size of the current player
     */
    public int handSizePlayer(){
        return playerQueue.peek().getHand().size();
    }

    /**
     * <b>currentCard</b>
     * Retrieves the string representation of the current card on top of the play deck.
     * <b>pre:</b> None.
     * <b>post:</b> The string representation of the current card has been retrieved.
     * @return the string representation of the current card
     */
    public String currentCard() {
        return deck.cardForId(deck.getPlayDeck().peek());
    }

    /**
     * <b>currentPlayerCardList</b>
     * Retrieves the string representation of the current player's hand.
     * <b>pre:</b> None.
     * <b>post:</b> The string representation of the current player's hand has been retrieved.
     * @return the string representation of the current player's hand
     */
    public String currentPlayerCardList() {
        List<String> list = playerQueue.peek().getHand();
        String result = "";

        for (int i = 0; i < list.size(); i++) {
            result +=  i+1 + ".  " + deck.cardForId(list.get(i)) + "\n";
        }
        return result;
    }

    /**
     * <b>isActiveSpecialcard</b>
     * Checks if there is an active special card on top of the play deck.
     * <b>pre:</b> None.
     * <b>post:</b> True if there is an active special card, false otherwise.
     * @return true if there is an active special card, false otherwise
     */
    public boolean isActiveSpecialcard() {
        String topCardId = deck.getPlayDeck().peek();
        Card topCard = deck.getCardTable().get(topCardId);
        boolean flag = false;

        if (topCard.getSpecialType() != Card.SpecialType.NONE) {
            if (activeSpecialcard) {
                flag = true;
            }
        }
        return flag;
    }

    //--------------------------------------------------------------------------------------------//

    /**
     * <b>changedColor</b>
     * Sets the auxiliary color for the change color effect.
     * <b>pre:</b> None.
     * <b>post:</b> The auxiliary color has been set for the change color effect.
     * @param i the index representing the color to set
     */
    public void changedColor(int i) {
        switch (i) {
            case 1:
                auxiliaryCard = Card.Color.BLUE;
                break;
            case 2:
                auxiliaryCard = Card.Color.GREEN;
                break;
            case 3:
                auxiliaryCard = Card.Color.RED;
                break;
            case 4:
                auxiliaryCard = Card.Color.YELLOW;
                break;

        }
        changeColor = false;
        changeColorController = true;
    }

    //---------------------------------------------------------------------------------------

    /**
     * <b>playCard</b>
     * Allows a player to play a card from their hand.
     * <b>pre:</b> The specified card index is valid and playable.
     * <b>post:</b> The specified card has been played, and its effects have been applied.
     * @param cardIndex the index of the card to play
     * @return true if the card was successfully played, false otherwise
     */
    public boolean playCard(int cardIndex) {
        boolean flag = false;

        Player currentPlayer = playerQueue.peek();
        String cardId = currentPlayer.getHand().get(cardIndex);
        Card playedCard = deck.getCardTable().get(cardId);

        // Obtiene la carta en la cima de la pila de juego para comparar
        String topCardId = deck.getPlayDeck().peek();
        Card topCard = deck.getCardTable().get(topCardId);

        if (!changeColor && !changeColorController) {
            auxiliaryCard = topCard.getColor();
        }

        // Identifica si es una carta de cambio de color
        boolean isChangeCard = playedCard.getSpecialType() == Card.SpecialType.CHANGE;

        // Comprueba si la carta es jugable según el color o el número para cartas normales
        boolean canPlayNormalCard = playedCard.getSpecialType() == Card.SpecialType.NONE &&
                (playedCard.getColor() == auxiliaryCard || playedCard.getNumber() == topCard.getNumber());

        // Permite jugar cartas especiales si coinciden en color o tipo especial con la carta en el montón,
        // excepto para las cartas de cambio de color, que siempre se pueden jugar.
        boolean canPlaySpecialCard = playedCard.getSpecialType() != Card.SpecialType.NONE &&
                (playedCard.getColor() == auxiliaryCard ||
                        playedCard.getSpecialType() == topCard.getSpecialType());

        // Combina las condiciones para determinar si la carta actual se puede jugar
        if (isChangeCard || canPlayNormalCard || canPlaySpecialCard) {
            // Mueve la carta al montón de descarte
            currentPlayer.removeCardFromHand(cardId);
            deck.getPlayDeck().push(cardId);

            // Verifica si el juego ha terminado
            gameOver = checkGameOver();
            flag = true;
            changeColorController = false;

            // Activa efectos especiales si corresponde
            if (playedCard.getSpecialType() != Card.SpecialType.NONE) {
                activeSpecialcard = true;
                if (isChangeCard) {
                    changeColor = true;
                }
            }
            nextTurn();
        }
        return flag;
    }

    //--------------------------------------------------------------------------------------------//

    /**
     * <b>handleSpecialCardEffect</b>
     * Handles the effects of the special card on top of the play deck.
     * <b>pre:</b> There is a special card on top of the play deck.
     * <b>post:</b> The effects of the special card have been applied, and a message describing the effect has been returned.
     * @return a message describing the effect of the special card
     */
    public String handleSpecialCardEffect() {
        // Obtiene la carta en la cima de la pila de juego para comparar
        String topCardId = deck.getPlayDeck().peek();
        Card topCard = deck.getCardTable().get(topCardId);

        String message = ""; // Mensaje que se retornará

        // Implementación de efectos basada en el tipo de carta especial
        switch (topCard.getSpecialType()) {
            case DRAW_TWO:
                drawCard(2);
                nextTurn();
                message = "A card with a +2 effect was used against you. You received 2 cards and lost your turn.";
                break;
            case SKIP:
                nextTurn();
                message = "A card with skip effect was used against you. You lost your turn.";
                break;
            case REVERSE:
                invert = !invert;
                nextTurn();
                nextTurn();
                message = "A card with reverse effect was used against you. The order of gameplay has been inverted. Wait for your new turn.";
                break;
            case CHANGE:
                message = "A color change card was activated. The color of the change card is: " + auxiliaryCard + ".";
                break;
        }
        activeSpecialcard = false;
        return message;
    }

    //--------------------------------------------------------------------------------------------//

    /**
     * <b>nextTurn</b>
     * Advances the game to the next turn, updating the player queue and game state.
     * <b>pre:</b> None.
     * <b>post:</b> The game has advanced to the next turn, and the player queue and game state have been updated accordingly.
     */
    public void nextTurn() {
        takeCard = true;
        if (!invert) {
            playerQueue.increasePriority();

            // Extrae al jugador actual de la cola de prioridad.
            Player currentPlayer = playerQueue.dequeue();

            // Vuelve a encolar al jugador actual con prioridad 1, colocándolo al final de la cola.
            playerQueue.enqueue(currentPlayer, 1);
        } else {
            playerQueue.prioritizeLowest();
        }
    }


    /**
     * <b>drawCard</b>
     * Draws a specified number of cards from the discard deck and adds them to the current player's hand.
     * <b>pre:</b> The specified number of cards is valid and drawable.
     * <b>post:</b> The specified number of cards has been drawn from the discard deck and added to the current player's hand.
     * @param numberCards the number of cards to draw
     */
    public void drawCard(int numberCards) {
        Player currentPlayer = playerQueue.peek();
        Queue<String> drawQueue = new Queue<>(); // cola temporal para las cartas a robar
        for (int i = 0; i < numberCards; i++) {
            drawQueue.enqueue(deck.getDiscardDeck().pop()); // Encolar la carta extraída del mazo de descarte
        }
        while (!drawQueue.isEmpty()) {
            currentPlayer.addCardToHand(drawQueue.dequeue()); // Añadir cartas a la mano del jugador desde la cola
        }
    }

    /**
     * <b>checkGameOver</b>
     * Checks if the game is over by determining if the current player has an empty hand.
     * <b>pre:</b> None.
     * <b>post:</b> True if the game is over, false otherwise.
     * @return true if the game is over, false otherwise
     */
    public boolean checkGameOver() {
        // Obtener al jugador actual de la cola de prioridad
        Player currentPlayer = playerQueue.peek();

        // Verificar si la mano del jugador actual está vacía
        if (currentPlayer.getHand().isEmpty()) {
            // Si la mano del jugador está vacía, el jugador ha ganado
            gameOver = true;
            return true;
        }
        return false;
    }

    //--------------------------------------------------------------------------------------------//

    public Deck getDeck() {
        return deck;
    }

    public PriorityQueue<Player> getPlayerQueue() {
        return playerQueue;
    }

    public Card.Color getAuxiliaryCard() {
        return auxiliaryCard;
    }
}

