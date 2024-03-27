package co.icesi.edu.model;

public class Card {
    public enum Color {
        BLUE, GREEN, RED, YELLOW, NONE
    }

    public enum SpecialType {
        DRAW_TWO, REVERSE, SKIP, WILD, WILD_DRAW_FOUR, NONE
    }

    private Color color;
    private int number;
    private SpecialType specialType;

    public Card(Color color, int number, SpecialType specialType) {
        this.color = color;
        this.number = number;
        this.specialType = specialType;
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
}