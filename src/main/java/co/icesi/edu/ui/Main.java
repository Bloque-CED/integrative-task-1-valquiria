package co.icesi.edu.ui;

import co.icesi.edu.model.GameController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    //--------------------------------------------------------------------------------------------//

    public GameController gameController;
    public Scanner scanner;

    //No puede existir throws en el main porque no hay nadie más que se encargue de manejarlo
    public Main() throws Exception {
        scanner = new Scanner(System.in);
        gameController = new GameController();
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.menuWelcome();
    }

    //--------------------------------------------------------------------------------------------//

    /**
     * <b>menuWelcome</b>
     * Displays the welcome menu, prompts the user to enter the number of players and their names, and starts the game.
     * <b>pre:</b> None.
     * <b>post:</b> The game has been started with the specified number of players and their names.
     */
    public void menuWelcome() {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("Welcome to UNO game!");
        System.out.println("---------------------------------------------------------");
        System.out.println("Enter number of players (2-5): ");
        System.out.print("   >");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();
        while (numPlayers < 2 || numPlayers > 5) {
            System.out.println("Invalid number of players. Please enter a number between 2 and 5.");
            System.out.print("   >");
            numPlayers = scanner.nextInt();
            scanner.nextLine();
        }

        List<String> playerNames = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Enter the name of player " + (i + 1) + ": ");
            System.out.print("   >");
            String name = scanner.nextLine();
            playerNames.add(name);
        }
        gameController.startGame(playerNames);
        gameTurn();
    }

    //--------------------------------------------------------------------------------------------//

    /**
     * <b>gameTurn</b>
     * Executes a turn in the game, including checking for a winner, displaying the current player's turn, and handling special card effects.
     * <b>pre:</b> None.
     * <b>post:</b> The game has advanced to the next turn and any special card effects have been applied.
     */
    public void gameTurn() {

        String actualPLayer = "";

        //VALIDAR SI ALGUIEN GANO
        if (gameController.isGameOver()) {
            System.out.println("\n---------------------------------------------------------");
            System.out.println(actualPLayer + " YOU ARE THE WINNER OF THE GAME, WELL DONE!!");
            System.out.println("---------------------------------------------------------");
            return;
        }
        actualPLayer = gameController.currentPlayer().toUpperCase();

        System.out.println("\n---------------------------------------------------------");
        System.out.println("IT'S THE TURN OF: " +  actualPLayer );

        if (gameController.isActiveSpecialcard()) {
            //String message = gameController.handleSpecialCardEffect();
            System.out.println(gameController.handleSpecialCardEffect());
        }else {
            toPlay();
        }
        gameTurn();
    }

    //--------------------------------------------------------------------------------------------//

    /**
     * <b>toPlay</b>
     * Manages the player's options during their turn, including playing a card, drawing a card, or skipping their turn.
     * <b>pre:</b> None.
     * <b>post:</b> The player has performed a valid action during their turn.
     */
    public void toPlay() {

        boolean takeCard = gameController.isTakeCard();

        System.out.println("The card on top of the discard stack is: " + gameController.currentCard());
        System.out.println("These are your cards: \n");

        if (takeCard) {
            System.out.println("0.  Take a card from the deck");
        } else {
            System.out.println("0.  Skip");
        }

        System.out.println("------------------------------");
        System.out.println(gameController.currentPlayerCardList());

        System.out.print("Select the card/option you want to play\n");
        System.out.print("   >");
        int action = scanner.nextInt();

        while (action < 0 || action > gameController.handSizePlayer()) {
            System.out.println("Invalid number. Please enter a number between 0 and " + gameController.handSizePlayer() + ".");
            System.out.print("   >");
            action = scanner.nextInt();
            scanner.nextLine();
        }

        if (action == 0) {
            if (takeCard){
                gameController.drawCard(1);
                gameController.setTakeCard(false);
            } else {
                gameController.nextTurn();
            }
        } else {

            int cardIndex = action - 1;
            scanner.nextLine();

            while (cardIndex < 0 || cardIndex >= gameController.handSizePlayer()) {
                System.out.println("The card index is invalid. Try again.");
                System.out.print("   >");
                cardIndex = scanner.nextInt() - 1;
                scanner.nextLine();
            }

            if (!gameController.playCard(cardIndex)) {
                System.out.println("\nYou can't play this card, select another card (1) or take a card from the deck (2).");
            }


            if (gameController.isChanged()) {
                System.out.println("To use the color change card, which color do you want to continue playing with?");
                System.out.println("1. BLUE,  2. GREEN,  3. RED,  4. YELLOW");
                System.out.print("   >");
                int color = scanner.nextInt();
                scanner.nextLine();
                while (color < 1 || color > 4) {
                    System.out.println("Invalid number. Please enter a number between 1 and 4.");
                    System.out.print("   >");
                    color = scanner.nextInt();
                    scanner.nextLine();
                }
                gameController.changedColor(color);
            }
        }
    }
}

