package co.icesi.edu.model;

import co.icesi.edu.structures.Queue;
import co.icesi.edu.structures.HashTable;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Queue<String> hand;
    private HashTable<String, Card> cardReferenceTable; //

    public Player(String name, HashTable<String, Card> cardReferenceTable) {
        this.name = name;
        this.hand = new Queue<>();
        if (cardReferenceTable == null) {
            throw new IllegalArgumentException("cardReferenceTable no puede ser null");
        }
        this.cardReferenceTable = cardReferenceTable;
    }

    public String getName() {
        return name;
    }

    public void addCardToHand(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("La carta no puede ser null");
        }
        String cardId = card.getId(); //
        hand.enqueue(cardId);
        //System.out.println("Carta agregada a la mano: " + cardId);
    }



    public boolean removeCardFromHand(Card card) {
        for (String cardId : hand) {
            Card handCard = cardReferenceTable.get(cardId);
            if (handCard != null && handCard.equals(card)) {
                hand.remove(cardId);
                return true;
            }
        }
        return false; //
    }

    public int getHandSize() {
        return hand.size();
    }

    public boolean hasCard(String cardId) {
        return hand.contains(cardId);
    }

    //
    public boolean hasPlayableCard(Card topCard, Card.Color currentColor) {
        for (String cardId : hand) {
            Card card = cardReferenceTable.get(cardId);
            if (card != null && card.isPlayable(topCard, currentColor)) {
                return true;
            }
        }
        return false;
    }

    public String playCard(Card topCard, Card.Color currentColor, int cardIndex) {
        if (cardIndex < 0 || cardIndex >= hand.size()) {
            return null;
        }

        String cardId = hand.get(cardIndex);
        Card cardToPlay = cardReferenceTable.get(cardId);
        if (cardToPlay == null || !cardToPlay.isPlayable(topCard, currentColor)) {
            return null;
        }

        hand.remove(cardId);
        return cardId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + "'s hand: ");
        for (String cardId : hand) {
            Card card = cardReferenceTable.get(cardId);
            if (card != null) {
                sb.append(card.toString()).append(", ");
            }
        }
        if (sb.length() > 2) {
            sb.delete(sb.length() - 2, sb.length()); //
        }
        return sb.toString();
    }



    public List<Card> getHand() {
        List<Card> cards = new ArrayList<>();
        for (String cardId : hand) {
            Card card = cardReferenceTable.get(cardId);
            if (card != null) {
                cards.add(card);
            }
        }
        return cards;
    }

}
