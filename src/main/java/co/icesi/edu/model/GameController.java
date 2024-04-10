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
        //Resumir codigo
        return deck.cardForId(deck.getPlayDeck().peek());
        //String x = deck.getPlayDeck().peek();
        //return deck.cardForId(x);
    }

    public boolean isChanged() {
        return changeColor;
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

        if (topCard.getSpecialType() != Card.SpecialType.NONE) {
            if (activeSpecialcard) {
                flag = true;
            }
        }
        return flag;
    }


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

    public boolean isTakeCard() {
        return takeCard;
    }

    public void setTakeCard(boolean takeCard) {
        this.takeCard = takeCard;
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






    // Método para manejar el efecto de las cartas especiales
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


    // Método para pasar al siguiente turno
    public void nextTurn() {

        takeCard = true;

        //No se esta usando esto
        //int i = playerQueue.size();

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


    // Método para robar una carta del mazo CON COLITA
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


    // Método para verificar el fin del juego
    private boolean checkGameOver() {
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
}

