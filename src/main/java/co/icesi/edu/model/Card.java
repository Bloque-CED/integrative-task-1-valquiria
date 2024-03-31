package co.icesi.edu.model;

import java.util.UUID;

public class Card {
    private final String id;
    public enum Color {
        BLUE, GREEN, RED, YELLOW, NONE
    }

    public enum SpecialType {
        DRAW_TWO, REVERSE, SKIP, WILD, NONE
    }

    private Color color;
    private int number;
    private SpecialType specialType;

    public Card(Color color, int number, SpecialType specialType) {
        this.id = UUID.randomUUID().toString();
        this.color = color;
        this.number = number;
        this.specialType = specialType;
    }


    //
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

    @Override
    public String toString() {
        if (specialType != SpecialType.NONE) {
            return "Special Card: " + specialType + (color != Color.NONE ? ", Color: " + color : "");
        } else {
            return "Number: " + number + ", Color: " + color;
        }
    }


    //
    public boolean isPlayable(Card topCard, Card.Color currentColor) {
        if (this.color == currentColor || this.color == Color.NONE || this.specialType != SpecialType.NONE) {
            return true;
        }
        if (this.specialType == SpecialType.NONE && topCard.specialType == SpecialType.NONE) {
            return this.number == topCard.number;
        }
        return false;
    }
}
