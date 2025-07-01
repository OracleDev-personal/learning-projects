package io;

import characters.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadManager {

    public static Player loadPlayer(String filePath) {
        if(filePath == null) {
            return null;
        }
        System.out.println("Attempting to load save...");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String name = reader.readLine();
            String race = reader.readLine();
            int level = Integer.parseInt(reader.readLine());
            int xp = Integer.parseInt(reader.readLine());
            int health = Integer.parseInt(reader.readLine());
            int maxHealth = Integer.parseInt(reader.readLine());
            int attPoints = Integer.parseInt(reader.readLine());
            int vitality = Integer.parseInt(reader.readLine());
            int strength = Integer.parseInt(reader.readLine());
            int agility = Integer.parseInt(reader.readLine());

            Player player = new Player(name);
            player.race = race;
            player.playerLevel = level;
            player.xp = xp;
            player.health = health;
            player.maxHealth = maxHealth;
            player.attributePoints = attPoints;
            player.attributeMap.put("vitality", vitality);
            player.attributeMap.put("strength", strength);
            player.attributeMap.put("agility", agility);
            System.out.println("Loaded save successfully!");
            return player;
        } catch (IOException e) {
            System.out.println("Error loading game: " + e.getMessage());
            return null;
        }
    }


}
