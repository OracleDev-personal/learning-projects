package game;

import characters.Enemy;
import characters.Player;
import characters.Entity;
import io.*;

import java.util.*;

public class BattleManager {

    private Set<Enemy> Enemies;

    public void battleHandler(Player player, String enemyRace, int numEnemies) {

        boolean battleComplete = false;

        Set<Entity> allCharacters = new HashSet<>();
        allCharacters.add(player); allCharacters.addAll(createEnemy(numEnemies, player.playerLevel, enemyRace));
        List<Entity> turnOrder = new ArrayList<>(allCharacters);
        turnOrder.sort(Comparator.comparingInt(Entity::getSpeed));

        do {
            for(Entity character : turnOrder) {
                if(player.health <= 0) {
                    System.out.println("You have died!");
                    break;
                }
                System.out.println(character.getName() + "'s Turn!");
                if (character instanceof Player) {
                    handlePlayerTurn(player);
                } else if (character instanceof Enemy enemy) {
                    handleEnemyTurn(enemy);
                }
            }
        } while (player.health > 0 && !battleComplete);

    }


    void postBattle(Player player) {
        ExperiencePoints experiencePoints = new ExperiencePoints();


        experiencePoints.playerXPCheck(player);
    }

    Set<Enemy> createEnemy(int howMany, int enemyMaxLevel, String race) {
        Set<Enemy> createdEnemies = new HashSet<>();
        for (int i = 0; i < howMany; i++) {
            Enemy enemy = new Enemy(createName(race, enemyMaxLevel), race, enemyMaxLevel);
            createdEnemies.add(enemy);
        }
        return createdEnemies;
    }

    // Creates generic names for enemies based on race passed in. Unique named enemies do not need this.
    String createName(String race, int level) {
        String rank = null;
        String name = null;

        switch(race) {
            case "human" -> {
                switch(level) {
                    case 1 -> rank = "Goon";
                    case 2 -> rank = "Bandit";
                    case 3 -> rank = "Highwayman";
                    case 4 -> rank = "Lieutenant";
                    case 5 -> rank = "Bandit Chief";
                }
                name = "Human";
                return name + " " + rank;
            }
            case "goblin" -> {
                switch(level) {
                    case 1 -> {return "Young Goblin";}
                    case 2 -> {return "Goblin";}
                    case 3 -> {return "Elder Goblin";}
                    case 4 -> {return "Goblin Chief";}
                    case 5 -> {return "Hobgoblin";}
                }
                name = "Goblin";
            }
            case "wolf" -> {
                switch(level) {
                    case 1 -> rank = "Young";
                    case 2 -> rank = "";
                    case 3 -> rank = "Adult";
                    case 4 -> rank = "Dire";
                    case 5 -> rank = "Alpha";
                }
                name = "Wolf";
                String fullName = rank + " " + name;
                return fullName.trim();
            }
        }
        return null;
    }

    void handlePlayerTurn(Player player) {
        // TO ADD - Need to create ability for player to take actions
    }

    void handleEnemyTurn(Enemy enemy) {
        // TO ADD - Need to create ability for player to take actions - will be able to reflect to enemies after.
    }



}