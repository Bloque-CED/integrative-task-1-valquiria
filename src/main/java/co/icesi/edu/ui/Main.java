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
        String actualPLayer = "";

        //VALIDAR SI ALGUIEN GANO
        if (gameController.isGameOver()) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("HAS GANADO: " + actualPLayer + "!");
            System.out.println("--------------------------------------------------");
            return;
        }
        actualPLayer = gameController.currentPlayer();
        actualPLayer = actualPLayer.toUpperCase();

        System.out.println("\n--------------------------------------------------");
        System.out.println("ES EL TURNO DE: " +  actualPLayer );

        if (gameController.isActiveSpecialcard()) {
            String message = gameController.handleSpecialCardEffect();
            System.out.println(message);
        }else {
            toPlay();
        }

        gameTurn();
    }

    public void toPlay() {
        int action = -1;

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

            if (!gameController.playCard(cardIndex)) {
                System.out.println("No puedes jugar esa carta, elige otra (1) o roba una del mazo (2).");
            }





            if (gameController.isChanged()) {
                System.out.println("A que color deseas cambiar la carta de Cambio de color?");
                System.out.println("1. Azul,  2. Verde,  3. Rojo,  4. Amarillo");
                System.out.print("   >");
                int color = scanner.nextInt();
                scanner.nextLine();
                while (color < 1 || color > 4) {
                    System.out.println("Número inválido. Por favor, ingrese un número entre 1 y 4.");
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

