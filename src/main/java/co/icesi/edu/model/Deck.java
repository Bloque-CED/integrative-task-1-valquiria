package co.icesi.edu.model;

import co.icesi.edu.structures.Stack;
import co.icesi.edu.structures.HashTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private Stack<String> drawPile;
    private Stack<String> discardPile;
    private HashTable<String, Card> cardTable;

    public Deck(HashTable<String, Card> cardTable) {
        this.drawPile = new Stack<>();
        this.discardPile = new Stack<>();
        this.cardTable = cardTable;
        initializeDrawPile();
    }

    private void initializeDrawPile() {
        // Creamos las cartas normales
        for (Card.Color color : Card.Color.values()) {
            if (color != Card.Color.NONE) {
                for (int number = 0; number <= 9; number++) {
                    Card card = new Card(color, number, Card.SpecialType.NONE);
                    String cardId = card.getId();
                    cardTable.put(cardId, card);
                    drawPile.push(cardId);
                    drawPile.push(cardId);
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
                    drawPile.push(drawTwoCardId);

                    Card reverseCard = new Card(color, -1, Card.SpecialType.REVERSE);
                    String reverseCardId = reverseCard.getId();
                    cardTable.put(reverseCardId, reverseCard);
                    drawPile.push(reverseCardId);

                    Card skipCard = new Card(color, -1, Card.SpecialType.SKIP);
                    String skipCardId = skipCard.getId();
                    cardTable.put(skipCardId, skipCard);
                    drawPile.push(skipCardId);
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            Card wildCard = new Card(Card.Color.NONE, -1, Card.SpecialType.WILD);
            String wildCardId = wildCard.getId();
            cardTable.put(wildCardId, wildCard);
            drawPile.push(wildCardId);

        }

        shuffleStack(drawPile);
    }

    public void shuffleStack(Stack<String> stack) {
        List<String> tempList = new ArrayList<>(stack.toList());
        Collections.shuffle(tempList, new Random());
        stack.clear();
        for (String cardId : tempList) {
            stack.push(cardId);
        }
    }

    public Card drawCard() {
        if (drawPile.isEmpty()) {
            refillDrawPileFromDiscardPile();
            if (drawPile.isEmpty()) {
                throw new RuntimeException("El mazo de robar está vacío incluso después de intentar rellenarlo.");
            }
        }
        String cardId = drawPile.pop();
        Card card = cardTable.get(cardId);
        if (card == null) {
            throw new IllegalStateException("Se intentó sacar una carta nula del mazo.");
        }
        return card;
    }

    public void refillDrawPileFromDiscardPile() {
        while (!discardPile.isEmpty()) {
            drawPile.push(discardPile.pop());
        }
        shuffleStack(drawPile);
    }

    public void discardCard(Card card) {
        discardPile.push(card.getId());
    }

    public Card topDiscardCard() {
        if (!discardPile.isEmpty()) {
            String cardId = discardPile.peek();
            return cardTable.get(cardId);
        }
        return null;
    }

    public int getDrawPileSize() {
        return drawPile.size();
    }

    public boolean isDrawPileEmpty() {
        return drawPile.isEmpty();
    }

    public List<String> stackToList(Stack<String> stack) {
        List<String> list = new ArrayList<>();
        for (String cardId : stack) {
            list.add(cardId);
        }
        return list;
    }
}



/*package co.icesi.edu.model;

//------------------------------------------------------------------------------------------------//

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//------------------------------------------------------------------------------------------------//

//EL JUEGO UNO TIENE 108 CARTAS
    //Cartas normales (76 cartas):
        //4 colores: Azul, verde, rojo y amarillo.
        //10 números: Del 0 al 9.
        //2 cartas de cada número y color: 19 cartas por cada color. salvo del 0 que es una por cada color
    //Cartas especiales (32 cartas):
        //8 cartas de "Roba dos": El siguiente jugador debe robar 2 cartas y perder su turno.
        //8 cartas de "Cambio de sentido": Hace que se invierta el orden secuencial de los jugadores.
        //8 cartas de "Salto": Hace que el siguiente jugador pierda su turno.
        //4 cartas de "Cambio color": Permiten elegir el siguiente color a jugar.
        //4 cartas de "Roba cuatro": El siguiente jugador debe robar 4 cartas y perder su turno.

//------------------------------------------------------------------------------------------------//

public class Deck {

    //------------------------------------------------------------------------------------------------//

    private List<Card> cards;
    private List<Card> discardPile;

    //------------------------------------------------------------------------------------------------//

    //Constructor
    public Deck() {
        // Inicializa las listas de cartas y de descarte
        this.cards = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        // Inicializa el mazo de cartas
        initializeDeck();
        // Mezcla las cartas en el mazo
        shuffleDeck();
    }


    //para inicializar el mazo de cartas
    public void initializeDeck() {
        // Mazo de 108 cartas (76 cartas normales + 32 cartas especiales)

        // Cartas normales
        // 19 cartas de cada color (salvo del 0 que es solo una)
        for (Card.Color color : Card.Color.values()) {
            for (int i = 0; i <= 9; i++) {
                // Se agrega una sola carta del número 0 por cada color
                if (i == 0) {
                    cards.add(new Card(color, i, Card.SpecialType.NONE));
                } else {
                    // Se agregan dos cartas de cada número del 1 al 9 por cada color
                    cards.add(new Card(color, i, Card.SpecialType.NONE));
                    cards.add(new Card(color, i, Card.SpecialType.NONE));
                }
            }
        }

        // Cartas especiales
        for (int i = 0; i < 8; i++) {
            cards.add(new Card(Card.Color.NONE, 0, Card.SpecialType.DRAW_TWO)); // Roba dos
            cards.add(new Card(Card.Color.NONE, 0, Card.SpecialType.REVERSE));   // Cambio de sentido
            cards.add(new Card(Card.Color.NONE, 0, Card.SpecialType.SKIP));      // Salto
        }
        for (int i = 0; i < 4; i++) {
            cards.add(new Card(Card.Color.NONE, 0, Card.SpecialType.WILD));               // Cambio de color
            cards.add(new Card(Card.Color.NONE, 0, Card.SpecialType.WILD_DRAW_FOUR));     // Roba cuatro
        }
    }


    //para mezclar las cartas en el mazo
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    //para distribuir cartas a los jugadores
    public void distributeCards(List<Player> players, int numCards) {
        for (Player player : players) {
            for (int i = 0; i < numCards; i++) {
                //si el mazo aún tiene cartas disponibles
                if (!cards.isEmpty()) {
                    //remueve la carta del mazo y la añade a la mano del jugador creo, no sé cómo sería esto
                    player.addCard(cards.remove(0));
                } else {
                    //aquí poner como que el mazo ya no tiene cartas o algo asi
                }
            }
        }
    }

    //para establecer la carta inicial en el mazo de descarte
    public void setInitialCard() {
        //si el mazo aún tiene cartas disponibles
        if (!cards.isEmpty()) {
            // Remueve la primera carta del mazo y la añade al mazo de descarte
            Card initialCard = cards.remove(0);
            discardPile.add(initialCard);
        } else {
            //aquí poner como que el mazo ya no tiene cartas o algo asi
        }
    }

    //------------------------------------------------------------------------------------------------//

}

//------------------------------------------------------------------------------------------------//

 */

