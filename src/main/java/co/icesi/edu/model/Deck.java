package co.icesi.edu.model;

import co.icesi.edu.structures.Stack;
import co.icesi.edu.structures.HashTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private Stack<String> playDeck; //pilaDeJuego
    private Stack<String> discardDeck; //pilaDeDescarte
    private HashTable<String, Card> cardTable; //tablaDeCartas

    /**
     * <b>Deck Constructor</b>
     * Initializes the play deck, discard deck, and card table, and initializes the draw pile.
     * <b>pre:</b> None.
     * <b>post:</b> The play deck, discard deck, and card table are initialized, and the draw pile is set up.
     * @throws Exception if an error occurs during initialization
     */
    public Deck() throws Exception {
        this.playDeck = new Stack<>();
        this.discardDeck = new Stack<>();
        cardTable = new HashTable<>();
        initializeDrawPile();
    }

    //--------------------------------------------------------------------------------------------//
    //Getters and setters
    public Stack<String> getDiscardDeck() {
        return discardDeck;
    }
    public Stack<String> getPlayDeck() {
        return playDeck;
    }
    public HashTable<String, Card> getCardTable() {
        return cardTable;
    }

    //--------------------------------------------------------------------------------------------//

    /**
     * <b>initializeDrawPile</b>
     * Initializes the draw pile with standard cards and special cards, shuffles them, and adds them to the discard deck.
     * <b>pre:</b> None.
     * <b>post:</b> The draw pile is initialized with standard and special cards, shuffled, and added to the discard deck.
     * @throws Exception if an error occurs during initialization
     */
    private void initializeDrawPile() throws Exception {

        List<String> listCards = new ArrayList<>();

        // cartas normales
        for (Card.Color color : Card.Color.values()) {
            if (color != Card.Color.NONE) {
                for (int number = 0; number <= 9; number++) {
                    Card card1 = new Card(color, number, Card.SpecialType.NONE);
                    Card card2 = new Card(color, number, Card.SpecialType.NONE);

                    String cardId1 = card1.getId();
                    String cardId2 = card2.getId();

                    cardTable.put(cardId1, card1);
                    cardTable.put(cardId2, card2);

                    listCards.add(cardId1);
                    listCards.add(cardId2);
                }
            }
        }

        //
        for (Card.Color color : Card.Color.values()) {
            if (color != Card.Color.NONE) {
                for (int i = 0; i < 2; i++) {
                    Card drawTwoCard = new Card(color, -1, Card.SpecialType.DRAW_TWO);

                    String drawTwoCardId = drawTwoCard.getId();
                    cardTable.put(drawTwoCardId, drawTwoCard);
                    listCards.add(drawTwoCardId);


                    Card reverseCard = new Card(color, -1, Card.SpecialType.REVERSE);
                    String reverseCardId = reverseCard.getId();
                    cardTable.put(reverseCardId, reverseCard);
                    listCards.add(reverseCardId);


                    Card skipCard = new Card(color, -1, Card.SpecialType.SKIP);
                    String skipCardId = skipCard.getId();
                    cardTable.put(skipCardId, skipCard);
                    listCards.add(skipCardId);
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            Card wildCard = new Card(Card.Color.NONE, -1, Card.SpecialType.CHANGE);

            String wildCardId = wildCard.getId();
            cardTable.put(wildCardId, wildCard);
            listCards.add(wildCardId);
        }

        Collections.shuffle(listCards);

        for (String cardId : listCards) {   // Se agregan las cartas revueltas a las pila
            discardDeck.push(cardId);
        }

    }

    /**
     * <b>cardForId</b>
     * Retrieves the card with the specified ID and returns its string representation.
     * <b>pre:</b> The card ID exists in the card table.
     * <b>post:</b> The card with the specified ID has been retrieved, and its string representation has been returned.
     * @param idCard the ID of the card to retrieve
     * @return the string representation of the card
     */
    public String cardForId(String idCard) {
        return cardTable.get(idCard).toString();
    }
}
