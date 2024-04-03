package co.icesi.edu.ui;

import co.icesi.edu.model.GameController;
import co.icesi.edu.model.Player;
import co.icesi.edu.model.Card;
import co.icesi.edu.structures.HashTable;



import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al juego UNO!");

        //
        System.out.print("Ingrese el número de jugadores (2-5): ");
        int numPlayers = scanner.nextInt();
        while (numPlayers < 2 || numPlayers > 5) {
            System.out.println("Número inválido de jugadores. Por favor, ingrese un número entre 2 y 5.");
            numPlayers = scanner.nextInt();
        }
        scanner.nextLine();


        List<String> playerNames = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Ingrese el nombre del jugador " + (i + 1) + ": ");
            String name = scanner.nextLine();
            playerNames.add(name);
        }
        HashTable<String, Card> cardReferenceTable = new HashTable<>();

        GameController gameController = new GameController(playerNames, 7, cardReferenceTable); //

        // Bucle principal del juego
        while (!gameController.isGameOver()) {
            for (Player player : gameController.getPlayers()) {
                System.out.println("\nEs el turno de " + player.getName());
                System.out.println("La carta en la cima del montón de descarte es: " + gameController.getTopDiscardCard());

                System.out.println("Tus cartas son: ");
                List<Card> hand = player.getHand(); //
                for (int i = 0; i < hand.size(); i++) {
                    System.out.println((i + 1) + ": " + hand.get(i)); //
                }



                int action = -1;

                // Dentro del bucle de juego en Main
                boolean playedSuccessfully = false;

                do {
                    System.out.println("¿Qué acción te gustaría realizar?");
                    System.out.println("1. Jugar una carta");
                    System.out.println("2. Robar del mazo");
                    action = Integer.parseInt(scanner.nextLine());

                    if (action == 1) {
                        System.out.print("Elige la carta que quieres jugar (1-" + hand.size() + "): ");
                        int cardIndex = Integer.parseInt(scanner.nextLine()) - 1;
                        playedSuccessfully = gameController.playCard(player, cardIndex, null);
                        if (!playedSuccessfully) {
                            System.out.println("No puedes jugar esa carta, elige otra (1) o roba una del mazo (2).");
                        }
                    } else if (action == 2) {
                        gameController.playerDrawCard(player);
                        playedSuccessfully = true;
                    } else {
                        System.out.println("Acción no reconocida, por favor intenta de nuevo.");
                    }
                } while (!playedSuccessfully);


                //
                if (gameController.isGameOver()) {
                    System.out.println("El juego ha terminado. ¡Felicidades " + player.getName() + ", has ganado!");
                    break;
                }
            }
        }

        scanner.close();
    }
}
