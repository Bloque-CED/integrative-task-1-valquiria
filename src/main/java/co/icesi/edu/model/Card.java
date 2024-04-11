package co.icesi.edu.model;

import java.util.UUID;

public class Card {
    private final String id;

    //--------------------------------------------------------------------------------------------//

    public enum Color {
        BLUE, GREEN, RED, YELLOW, NONE
    }

    public enum SpecialType {
        DRAW_TWO, REVERSE, SKIP, CHANGE, NONE
    }

    //--------------------------------------------------------------------------------------------//

    private Color color;
    private int number;
    private SpecialType specialType;

    /**
     * <b>Constructor of the Card class</b>
     * Creates a new card with the specified color, number, and special type.
     * <b>pre:</b> None.
     * <b>post:</b> A new card with the specified color, number, and special type has been created.
     * @param color the color of the card
     * @param number the number of the card
     * @param specialType the special type of the card
     */
    public Card(Color color, int number, SpecialType specialType) {
        this.id = UUID.randomUUID().toString();
        this.color = color;
        this.number = number;
        this.specialType = specialType;
    }

    //--------------------------------------------------------------------------------------------//

    //Getters and setters
    public String getId() {
        return id;
    }
    public Color getColor() {
        return color;
    }
    public int getNumber() {
        return number;
    }
    public SpecialType getSpecialType() {
        return specialType;
    }

    //--------------------------------------------------------------------------------------------//

    /**
     * <b>toString</b>
     * Returns a string representation of the card, including its number and color (and special type, if applicable).
     * <b>pre:</b> None.
     * <b>post:</b> A string representation of the card has been returned.
     * @return a string representation of the card
     */
    @Override
    public String toString() {
        if (specialType != SpecialType.NONE) {
            return "Special Card: " + specialType + (color != Color.NONE ? ", Color: " + color : "");
        } else {
            return "Number: " + number + ", Color: " + color;
        }
    }
}
