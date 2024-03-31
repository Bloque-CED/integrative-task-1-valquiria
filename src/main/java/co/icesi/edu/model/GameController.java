package co.icesi.edu.model;

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
    private Card.Color currentColor;
    private boolean isReverse = false;

    public GameController(List<String> playersNames, int numCards, HashTable<String, Card> cardReferenceTable) {
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
        currentColor = initialCard.getColor();
    }


    private void initializeGame() {
        Card initialCard;
        do {
            initialCard = deck.drawCard();
        } while (initialCard.getSpecialType() != Card.SpecialType.NONE);

        deck.discardCard(initialCard);
        currentColor = initialCard.getColor();
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
        currentColor = cardToPlay.getColor();

        if (player.getHandSize() == 0) {
            System.out.println(player.getName() + " ha ganado el juego!");
            return true;
        }

        updateTurnOrder();
        return true;
    }

    private boolean isPlayable(Card card) {
        return card.getColor() == currentColor || card.getColor() == Card.Color.NONE || card.getSpecialType() != Card.SpecialType.NONE;
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
            case WILD:
                //lógica elegir el color
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
