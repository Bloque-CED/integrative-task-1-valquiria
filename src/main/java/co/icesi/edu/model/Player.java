package co.icesi.edu.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<String> hand;

    /**
     * <b>Constructor of the Player class</b>
     * Creates a new player with the specified name and an empty hand.
     * <b>pre:</b> The player's name must not be null.
     * <b>post:</b> A new player with the specified name and an empty hand has been created.
     * @param name the name of the player
     */

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    //--------------------------------------------------------------------------------------------//

    //Getters and setters
    public String getName() {
        return name;
    }
    public List<String> getHand() {
        return new ArrayList<>(hand);
    }

    //--------------------------------------------------------------------------------------------//

    /**
     * <b>addCardToHand</b>
     * Adds a card ID to the player's hand.
     * <b>pre:</b> The card ID cannot be null or empty.
     * <b>post:</b> The card ID has been added to the player's hand.
     * @param cardId the ID of the card to add to the hand
     */
    public void addCardToHand(String cardId) {
        if (cardId == null || cardId.isEmpty()) {
            throw new IllegalArgumentException("El ID de la carta no puede ser null o vacío");
        }
        hand.add(cardId);
    }

    /**
     * <b>removeCardFromHand</b>
     * Removes a card ID from the player's hand, based on ID equality.
     * <b>pre:</b> None.
     * <b>post:</b> The card ID has been removed from the player's hand if it was present.
     * @param cardId the ID of the card to remove from the hand
     * @return true if the card ID has been removed, false if it was not present
     */
    public boolean removeCardFromHand(String cardId) {
        return hand.remove(cardId);
    }




}
