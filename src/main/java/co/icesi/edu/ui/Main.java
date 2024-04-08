package co.icesi.edu.ui;

import co.icesi.edu.model.GameController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
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


    public void menuWelcome() {
        System.out.println("Welcome to UNO game!");
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

    public void gameTurn() {
        String actualPLayer = "";

        //VALIDAR SI ALGUIEN GANO
        if (gameController.isGameOver()) {
            System.out.println("\n---------------------------------------------------------");
            System.out.println(actualPLayer + " YOU ARE THE WINNER OF THE GAME, WELL DONE!!");
            System.out.println("---------------------------------------------------------");
            return;
        }
        actualPLayer = gameController.currentPlayer();
        actualPLayer = actualPLayer.toUpperCase();

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

    public void toPlay() {
        //int action = -1;

        System.out.println("The card on top of the discard stack is: " + gameController.currentCard());
        System.out.println("These are your cards: \n");
        System.out.println(gameController.currentPlayerCardList());

        System.out.println("What do you want to do?");
        System.out.println("1. Play a card.");
        //No estoy segura de que esto sea así, el punto 2 la traduccion
        System.out.println("2. Take a card from the deck.");
        System.out.print("   >");
        int action = scanner.nextInt();
        scanner.nextLine();
        while (action < 1 || action > 2) {
            System.out.println("Invalid number. Please enter a number between 1 and 2.");
            System.out.print("   >");
            action = scanner.nextInt();
            scanner.nextLine();
        }

        if (action == 1) {
            System.out.print("Select the card you want to play\n");
            System.out.print("   >");
            int cardIndex = scanner.nextInt() - 1;
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
        } else {
            gameController.drawCard(1);
            gameController.nextTurn();
        }
    }
}

