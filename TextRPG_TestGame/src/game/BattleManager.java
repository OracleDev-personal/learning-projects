package game;

import characters.Enemy;
import characters.Player;

import java.util.HashSet;
import java.util.Set;

public class BattleManager {

    private Set<Enemy> Enemies;

    public void battleHandler(Player player, String enemyRace, int numEnemies) {


    }


    void postBattle(Player player) {
        ExperiencePoints experiencePoints = new ExperiencePoints();
        experiencePoints.playerXPCheck(player);
    }

    Enemy createEnemy(int howMany, int enemyMaxLevel) {
        Set<Enemy> createdEnemies = new HashSet<>();
        for (int i = 0; i < howMany; i++) {

        }

        return null;
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



}