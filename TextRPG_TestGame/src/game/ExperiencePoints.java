package game;

import characters.Player;

public class ExperiencePoints {

    public final static int[] xpLevels = {0, 10, 30, 60, 100, 150};
    public final static int[] healthXPLevel = {10, 15, 25, 40, 60};

    public void playerXPCheck(Player player) {
        int[] healthXPLevel = {10, 15, 25, 40, 60};
        int[] xpLevels = {0, 10, 30, 60, 100, 150};
        while (player.xp >= xpLevels[player.playerLevel]) {
            player.playerLevel++;
            player.attributePoints++;
            System.out.println("You have leveled up!");
            if(player.playerLevel == 5) {
                System.out.println("You have reached max level!");
            }
            System.out.println();
            player.maxHealth = healthXPLevel[player.playerLevel - 1];
            player.health = player.maxHealth;
        }
    }
}
