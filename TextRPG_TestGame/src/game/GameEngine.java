package game;

import characters.Player;
import items.Inventory;
import io.SaveManager;
import io.LoadManager;
import world.TutorialInstance;

import java.util.Map;
import java.util.Scanner;

public class GameEngine {

    static String playerInput;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
        Player player = null;
        do {
            playerInput = engine.homeMenu();
            switch (playerInput) {
            case "new game" -> player = engine.newGame();
            case "load game" -> player = LoadManager.loadPlayer(SaveManager.selectSaveFile());
            case "exit" -> System.exit(0);
            }
            if (player == null) {
                engine.clearScreen();
                playerInput = "";
                System.out.println("No save files found. Returning to home menu...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Error attempting to go back to home menu. Exiting...");
                    System.exit(1);
                }
            }
        } while (player == null);





        scanner.close();
    }

    public String homeMenu() {
        Map<String, String> choiceMap = Map.of(
                "1", "new game",
                "new game", "new game",
                "2", "load game",
                "load game", "load game",
                "3", "exit",
                "exit", "exit"
        );
        do {
            clearScreen();
            System.out.println("===============");
            System.out.println("Adventurer Java");
            System.out.println("===============\n");
            System.out.println("""
                1. New Game
                2. Load Game
                3. Exit
                """);
            System.out.print("Make a choice: ");
            playerInput = scanner.nextLine().trim().toLowerCase();
            System.out.println(playerInput);
        } while (!choiceMap.containsKey(playerInput));
        playerInput = choiceMap.get(playerInput);
        return playerInput;

    }

    public Player newGame() {
        System.out.println("Welcome to Adventurer Java!");
        System.out.print("Enter a name (default will be Java): ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            name = "Java";
        }
        return new Player(name);
    }

    void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    void loadGame(Player player) {
        if (playerInput.equals("new game") || playerInput.equals("1")) {
            System.out.println("\nStarting new game. Launching tutorial");
        } else {
            System.out.println("\nLoading game...");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("\nError attempting to load. Exiting...");
            System.exit(1);
        }

        if (playerInput.equals("new game") || playerInput.equals("1")) {
            TutorialInstance.doTutorial(player);
        } else {
            // TO ADD - Loading current game.
        }
    }
}
