package io;

import characters.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SaveManager {

    public static void savePlayer(Player player) {
        String filename = "saves/" + player.getName().toLowerCase().replace(" ", "_") + "_save.txt";

        try {
            File saveDir = new File("saves");
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }

            FileWriter writer = new FileWriter(filename);
            writer.write(player.getName() + "\n");
            writer.write(player.race + "\n");
            writer.write(player.playerLevel + "\n");
            writer.write(player.xp + "\n");
            writer.write(player.health + "\n");
            writer.write(player.maxHealth + "\n");
            writer.write(player.attributePoints + "\n");
            writer.write(player.attributeMap.get("vitality") + "\n");
            writer.write(player.attributeMap.get("strength") + "\n");
            writer.write(player.attributeMap.get("agility") + "\n");
            writer.close();
            System.out.println(filename + ": Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    public static String selectSaveFile() {
        String directoryPath = "saves/";
        File folder = new File(directoryPath);

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (files == null || files.length == 0) {
            System.out.println("No save files found.");
            return null;
        }

        System.out.println("Available save files:");
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i].getName());
        }

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        do {
            System.out.print("Choose a file to load (1-" + files.length + "): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {}
        } while (choice < 1 || choice > files.length);

        return files[choice - 1].getAbsolutePath();
    }

}
