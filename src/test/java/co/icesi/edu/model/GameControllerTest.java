package co.icesi.edu.model;

import co.icesi.edu.structures.Stack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class GameControllerTest {

    private GameController gameController;
    private List<String> playerNames;
    private GameController gameController0;
    private List<String> playerNames0;

    private GameController gameController1;
    private List<String> playerNames1;
    @Before
    public void setUpNormal() throws Exception {
        gameController = new GameController();
        playerNames = new ArrayList<>();
        playerNames.add("Player 1");
        playerNames.add("Player 2");
        playerNames.add("Player 3");
        gameController.startGame(playerNames);
    }

    @Before
    public void setUpExtreme() throws Exception {
        gameController0 = new GameController();
        playerNames0 = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            playerNames0.add("Player " + i);
        }
        gameController0.startGame(playerNames0);
    }

    @Before
    public void setUpSpecial() throws Exception {
        gameController1 = new GameController();
        playerNames1 = new ArrayList<>();
        playerNames1.add("Player 1");
        playerNames1.add("Player 2");
        gameController1.startGame(playerNames1);

        Stack<String> playDeck = new Stack<>();
        List<String> listCards = new ArrayList<>();

        // cartas normales y todas rojas
        for (Card.Color color : Card.Color.values()) {
            if (color == Card.Color.RED) {
                for (int number = 0; number <= 9; number++) {
                    Card card1 = new Card(color, number, Card.SpecialType.NONE);
                    String cardId1 = card1.getId();
                    gameController1.getDeck().getCardTable().put(cardId1, card1);
                    listCards.add(cardId1);
                    gameController1.getDeck().getPlayDeck().push(cardId1);
                }
            }
        }
        // Preparar cartas especiales y añadirlas al inicio del mazo de juego
        String block = new Card(Card.Color.RED, -1, Card.SpecialType.SKIP).getId();
        String reverseCard = new Card(Card.Color.RED, -1, Card.SpecialType.REVERSE).getId();
        String none = new Card(Card.Color.RED, 1, Card.SpecialType.NONE).getId();
        String drawTwoCard = new Card(Card.Color.RED, -1, Card.SpecialType.DRAW_TWO).getId();

        gameController1.getDeck().getCardTable().put(block, new Card(Card.Color.RED, -1, Card.SpecialType.SKIP));
        gameController1.getDeck().getCardTable().put(reverseCard, new Card(Card.Color.RED, -1, Card.SpecialType.REVERSE));
        gameController1.getDeck().getCardTable().put(none, new Card(Card.Color.RED, 1, Card.SpecialType.NONE));
        gameController1.getDeck().getCardTable().put(drawTwoCard, new Card(Card.Color.RED, -1, Card.SpecialType.DRAW_TWO));

        playDeck.push(block);
        playDeck.push(drawTwoCard);
        playDeck.push(reverseCard);
        playDeck.push(none);
        // Añadir cartas
        for (String cardId : listCards) {
            playDeck.push(cardId);
        }

        gameController1.getDeck().setPlayDeck(playDeck);


        List<String> hand1 = new ArrayList<>();
        hand1.add(reverseCard); //
        hand1.add(drawTwoCard); //
        hand1.add(none); //
        hand1.add(none);
        gameController1.getPlayerQueue().peek().setHand(hand1);

        gameController1.nextTurn();


        List<String> hand2 = new ArrayList<>();
        hand2.add(none); //
        hand2.add(block); //
        gameController1.getPlayerQueue().peek().setHand(hand2);

        gameController1.nextTurn();
    }
    @Test
    public void testStartGame() {
        Assert.assertFalse(gameController.isGameOver());
        Assert.assertEquals(3, gameController.getPlayerQueue().size());
    }

    @Test
    public void testCurrentPlayer() {
        String currentPlayer = gameController.currentPlayer();
        Assert.assertEquals("Player 3", currentPlayer);
    }
    @Test
    public void testHandSizePlayer() {
        int handSize = gameController.handSizePlayer();
        Assert.assertEquals(7, handSize);
    }

    @Test
    public void testCurrentCard() {
        String currentCard = gameController.currentCard();
        Assert.assertNotNull(currentCard);
    }

    @Test
    public void testCurrentPlayerCardList() {
        String currentPlayerCardList = gameController.currentPlayerCardList();
        Assert.assertFalse(currentPlayerCardList.isEmpty());
    }

    @Test
    public void testIsActiveSpecialcard() {
        boolean isActiveSpecialcard = gameController.isActiveSpecialcard();
        Assert.assertFalse(isActiveSpecialcard);
    }
    @Test
    public void testChangedColor() {
        gameController.changedColor(1);
        Assert.assertEquals(Card.Color.BLUE, gameController.getAuxiliaryCard());
    }

    @Test
    public void testHandleSpecialCardEffect() {
        String message = gameController.handleSpecialCardEffect();
        Assert.assertNotNull(message);
    }

    @Test
    public void testNextTurn() {
        String firstPlayer = gameController.currentPlayer();
        gameController.nextTurn();
        String secondPlayer = gameController.currentPlayer();
        Assert.assertNotEquals(firstPlayer, secondPlayer);
    }
    @Test
    public void testDrawCard() {
        int initialHandSize = gameController.handSizePlayer();
        gameController.drawCard(1);
        Assert.assertEquals(initialHandSize + 1, gameController.handSizePlayer());
    }

    @Test
    public void testCheckGameOver() {
        boolean isGameOver = gameController.checkGameOver();
        Assert.assertFalse(isGameOver);
    }

    @Test
    public void testMaxPlayers() {
        Assert.assertEquals(5, gameController0.getPlayerQueue().size());
    }

    @Test
    public void testNoCardsToDraw() {
        while (!gameController0.getDeck().getDiscardDeck().isEmpty()) {
            gameController0.getDeck().getDiscardDeck().pop();
        }
        try {
            gameController0.drawCard(1);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testImmediateSpecialCard() {
        String specialCardId = "special_card";
        Card specialCard = new Card(Card.Color.NONE, -1, Card.SpecialType.SKIP);
        gameController0.getDeck().getCardTable().put(specialCardId, specialCard);
        gameController0.getDeck().getPlayDeck().push(specialCardId);

        String effectMessage = gameController0.handleSpecialCardEffect();
        Assert.assertEquals("A card with skip effect was used against you. You lost your turn.", effectMessage);
    }
    @Test
    public void testPlayLastCard() {
        Player currentPlayer = gameController.getPlayerQueue().peek();
        Card playableCard = new Card(Card.Color.BLUE, 5, Card.SpecialType.NONE);
        String cardId = playableCard.getId();

        gameController.getDeck().getCardTable().put(cardId, playableCard);
        gameController.getDeck().getPlayDeck().push(cardId);

        List<String> hand = new ArrayList<>();
        hand.add(cardId);
        currentPlayer.setHand(hand);

        Assert.assertEquals(cardId, gameController.getDeck().getPlayDeck().peek());

        Assert.assertTrue(gameController.playCard(0));
        Assert.assertTrue(gameController.isGameOver());
    }

    // Special case
    @Test
    public void testSpecial() {
        // Asumiendo que Player 1 comienza con la carta REVERSE

        Assert.assertEquals("Player 2 debería comenzar el juego", "Player 2", gameController1.currentPlayer());

        Assert.assertTrue("Player 2 juega la carta REVERSE", gameController1.playCard(0));


        Assert.assertEquals("El orden de los turnos debería invertirse, por derecha e izquierda estaría el Player 1",
                "Player 1", gameController1.currentPlayer());

        Assert.assertTrue("Player 1 ", gameController1.playCard(0));
        Assert.assertTrue("Player 2 ", gameController1.playCard(0));

        // Verificar que Player 1 haya recibido dos cartas adicionales

        Assert.assertTrue(gameController1.isActiveSpecialcard());
        Assert.assertEquals("A card with a +2 effect was used against you. You received 2 cards and lost your turn.",
                gameController1.handleSpecialCardEffect());
        Assert.assertTrue("Player 2 ", gameController1.playCard(0));

        Assert.assertEquals("Player 1 debería tener dos cartas adicionales después de DRAW TWO o sea 3", 3,
                gameController1.getPlayerQueue().peek().getHand().size());     //getHand().size()); // Suponiendo que empezó con 1 carta

        Assert.assertTrue("Player 1 ", gameController1.playCard(0)); //bloquea y gana
        Assert.assertTrue("Player 2 ", gameController1.playCard(0));

        Assert.assertTrue(gameController1.isGameOver());

    }
}