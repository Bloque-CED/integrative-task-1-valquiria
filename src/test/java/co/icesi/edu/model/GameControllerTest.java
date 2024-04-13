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
        playerNames1.add("Alice");
        playerNames1.add("Bob");
        playerNames1.add("Carol");
        gameController1.startGame(playerNames1);

        Stack<String> specialPlayDeck = new Stack<>();
        String skipCard = new Card(Card.Color.RED, -1, Card.SpecialType.SKIP).getId();
        String reverseCard = new Card(Card.Color.YELLOW, -1, Card.SpecialType.REVERSE).getId();
        String drawTwoCard = new Card(Card.Color.GREEN, -1, Card.SpecialType.DRAW_TWO).getId();

        gameController1.getDeck().getCardTable().put(skipCard, new Card(Card.Color.RED, -1, Card.SpecialType.SKIP));
        gameController1.getDeck().getCardTable().put(reverseCard, new Card(Card.Color.YELLOW, -1, Card.SpecialType.REVERSE));
        gameController1.getDeck().getCardTable().put(drawTwoCard, new Card(Card.Color.GREEN, -1, Card.SpecialType.DRAW_TWO));

        specialPlayDeck.push(drawTwoCard);
        specialPlayDeck.push(reverseCard);
        specialPlayDeck.push(skipCard);
        gameController1.getDeck().setPlayDeck(specialPlayDeck);
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

}