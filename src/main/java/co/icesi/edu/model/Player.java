package co.icesi.edu.model;

import co.icesi.edu.structures.Queues;

public class Player {
    private String name;
    private Queues<Card> cards;

    public Player(String name, Queues<Card> cards) {
        this.name = name;
        this.cards = cards;
    }

    //Agregar una carta al mazo del jugador
    public void addCard(Card newCard){
        cards.enqueue(newCard);
    }

    // Método para verificar si el jugador puede jugar la carta que tiene
    //Se comento para poder subirlo ya que aun no se han implementado los metodos de la carta
    public boolean canPlay(Card card) {
        Card actualCard = cards.peek();

        /*if(actualCard.getColor() == card.getColor() ||
                actualCard.getNumber() == card.getNumber() ||
                actualCard.getType() == card.getType()){
            return true;
        } else if(!actualCard.getType().equals("NORMAL")){
            return true;
        }*/
        return false;
    }

    //Elimina la carta jugada, simpre jugaria la primer carta del mazo
    public void playCard(){
        if(canPlay(cards.peek())){
            cards.dequeue();
        }
    }

    //Indica si el jugador ya no tiene cartas, de esta forma gana
    public boolean isWinner(){
        return cards.isEmpty();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Queues<Card> getCards() {
        return cards;
    }

    public void setCards(Queues<Card> cards) {
        this.cards = cards;
    }
}