package co.icesi.edu.ui;

import co.icesi.edu.model.GameController;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public GameController gameController;
    public Scanner scanner;

    public Main() throws Exception {
        scanner = new Scanner(System.in);
        gameController = new GameController();
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.wellcome();
    }

    //---------------------------------------------------------------------------------------

    public void wellcome() {
        System.out.println("Bienvenido al juego UNO!");
        System.out.println("Ingrese el número de jugadores (2-5): ");
        System.out.print("   >");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();
        while (numPlayers < 2 || numPlayers > 5) {
            System.out.println("Número inválido de jugadores. Por favor, ingrese un número entre 2 y 5.");
            System.out.print("   >");
            numPlayers = scanner.nextInt();
            scanner.nextLine();
        }

        List<String> playerNames = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Ingrese el nombre del jugador " + (i + 1) + ": ");
            System.out.print("   >");
            String name = scanner.nextLine();
            playerNames.add(name);
        }
        gameController.startGame(playerNames);

        gameTurn();
    }

    public void gameTurn() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("Es el turno de " +  gameController.currentPlayer());

        if (gameController.isActiveSpecialcard()) {
            gameController.handleSpecialCardEffect();
            System.out.println("NO PUEDES JUGAR PORQUE TE BLOQUEARON 0 COMISTE 2");
        }else {
            toPlay();
            gameTurn();
        }

        gameTurn();
    }

    public void toPlay() {
        while (!gameController.isGameOver()) {
            int action = -1;
            boolean playedSuccessfully = false;
            do {
                System.out.println("La carta en la cima del montón de descarte es: " + gameController.currentCard());
                System.out.println("Tus cartas son: \n");
                System.out.println(gameController.currentPlayerCardList());

                System.out.println("¿Qué acción te gustaría realizar?");
                System.out.println("1. Jugar una carta");
                System.out.println("2. Robar del mazo");
                System.out.print("   >");
                action = scanner.nextInt();
                scanner.nextLine();
                while (action < 1 || action > 2) {
                    System.out.println("Número inválido. Por favor, ingrese un número entre 1 y 2.");
                    System.out.print("   >");
                    action = scanner.nextInt();
                    scanner.nextLine();
                }

                if (action == 1) {
                    System.out.print("Elige la carta que quieres jugar");
                    System.out.print("   >");
                    int cardIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    while (cardIndex < 0 || cardIndex >= gameController.handSizePlayer()) {
                        System.out.println("Índice de carta inválido. Intentalo de nuevo");
                        System.out.print("   >");
                        cardIndex = scanner.nextInt() - 1;
                        scanner.nextLine();
                    }

                    playedSuccessfully = gameController.playCard(cardIndex);

                    if (!playedSuccessfully) {
                        System.out.println("No puedes jugar esa carta, elige otra (1) o roba una del mazo (2).");
                    } else {
                        gameController.nextTurn();
                    }

                    if (gameController.isGameOver()) {
                        System.out.println("El juego ha terminado. ¡Felicidades " + gameController.currentPlayer() + ", has ganado!");
                        break;
                    }

                } else {
                    gameController.drawCard(1);
                    playedSuccessfully = true;
                    gameController.nextTurn();
                }
            } while (!playedSuccessfully);

        }
    }
}

