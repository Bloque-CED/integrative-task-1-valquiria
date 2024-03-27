package co.icesi.edu.model;

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
        //mazo de 108 cartas (76 cartas normales + 32 cartas especiales)

        //cartas normales
        //19 cartas de cada color (salvo del 0 que es solo una)
        for (Card.Color color : Card.Color.values()) {
            for (int i = 1; i <= 9; i++) {
                for (int j = 0; j < (i == 0 ? 1 : 2); j++) {
                    cards.add(new Card(color, i, null));
                }
            }
        }

        //cartas especiales
        for (int i = 0; i < 8; i++) {
            cards.add(new Card(Card.Color.NONE, 0, Card.SpecialType.DRAW_TWO)); // Roba dos
            cards.add(new Card(Card.Color.NONE, 0,  Card.SpecialType.REVERSE));   // Cambio de sentido
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

