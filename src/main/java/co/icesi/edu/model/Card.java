package co.icesi.edu.model;

import java.util.UUID;

public class Card {
    private final String id;
    public enum Color {
        BLUE, GREEN, RED, YELLOW, NONE
    }

    public enum SpecialType {
        DRAW_TWO, REVERSE, SKIP, CHANGE, NONE
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

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        if (specialType != SpecialType.NONE) {
            return "Special Card: " + specialType + (color != Color.NONE ? ", Color: " + color : "");
        } else {
            return "Number: " + number + ", Color: " + color;
        }
    }
}
