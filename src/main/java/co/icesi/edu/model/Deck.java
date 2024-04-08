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

    public Deck() throws Exception {
        this.playDeck = new Stack<>();
        this.discardDeck = new Stack<>();
        cardTable = new HashTable<>();
        initializeDrawPile();
    }

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

        //shuffleStack(listCards); // se revuelven todas las cartas que se crearon en orden
        Collections.shuffle(listCards);

        for (String cardId : listCards) {   // Se agregan las cartas revueltas a las pila
            discardDeck.push(cardId);
        }

    }

    //Puede omitirse este metodo
    /*public void shuffleStack(List<String> listCards) {
        Collections.shuffle(listCards);
    }*/

    public Stack<String> getDiscardDeck() {
        return discardDeck;
    }

    public Stack<String> getPlayDeck() {
        return playDeck;
    }

    public String cardForId(String idCard) {
        return cardTable.get(idCard).toString();
    }

    public HashTable<String, Card> getCardTable() {
        return cardTable;
    }
}
