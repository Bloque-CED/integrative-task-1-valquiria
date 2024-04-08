package co.icesi.edu.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<String> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // Añade el ID de una carta a la mano del jugador
    public void addCardToHand(String cardId) {
        if (cardId == null || cardId.isEmpty()) {
            throw new IllegalArgumentException("El ID de la carta no puede ser null o vacío");
        }
        hand.add(cardId);
    }

    // Elimina el ID de una carta de la mano del jugador, basándose en la igualdad del ID
    public boolean removeCardFromHand(String cardId) {
        return hand.remove(cardId);
    }

    // Devuelve el tamaño de la mano del jugador
    public int getHandSize() {
        return hand.size();
    }


    // Devuelve la mano del jugador como una lista de IDs de cartas
    public List<String> getHand() {
        return new ArrayList<>(hand); // Devuelve una copia de la mano para evitar modificaciones externas
    }


}
