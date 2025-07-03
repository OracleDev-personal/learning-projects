package characters;

import items.Inventory;

import java.util.*;

import static game.ExperiencePoints.healthXPLevel;
import static game.ExperiencePoints.xpLevels;

public class Player extends Entity {

    public int playerLevel;
    public int xp;
    public int attributePoints;
    public Inventory inventory;


    public Player(String name) {
        super(name);
        race = "Human";
        maxHealth = 10;
        health = 10 + (attributeMap.get("vitality") * 3);
        playerLevel = 1;
        xp = 0;
        attributePoints = 0;
        attributeMap = new HashMap<>(Map.of(
                "vitality", 0,
                "strength", 0,
                "agility", 0
        ));
        inventory = new Inventory();
    }

    public void addAttributes(Scanner scanner) {
        String playerChoice;
        Set<String> validChoices = Set.of("vitality", "strength", "agility");

        while (attributePoints > 0) {
            System.out.println("Attributes:\n" +
                    "    Vitality: " + attributeMap.get("vitality") + "\n" +
                    "    Strength: " + attributeMap.get("strength") + "\n" +
                    "     Agility: " + attributeMap.get("agility"));
            System.out.println(attributePoints + " attribute points available to add.");
            System.out.print("Enter the attributes you wish to increase: ");
            playerChoice = scanner.nextLine().toLowerCase().trim();
            if (!validChoices.contains(playerChoice)) {
                System.out.println("Invalid choice! Try: vitality, strength, agility");
                continue;
            }
            attributeMap.put(playerChoice, attributeMap.getOrDefault(playerChoice, 0) + 1);
            attributePoints--;
            System.out.println(playerChoice + " increased by 1!\n");
        }

        System.out.println("All attribute points have been used.");
    }

    @Override
    public String toString() {
        return "Player Info:\n" +
                "Name: " + getName() + "\n" +
                "Race: " + race + "\n" +
                "Player Level: " + playerLevel + "\n" +
                "XP: " + xp + "/" + xpLevels[playerLevel] + "\n" +
                "Attributes:\n" +
                "     Vitality: " + attributeMap.get("vitality") + "\n" +
                "     Strength: " + attributeMap.get("strength") + "\n" +
                "     Agility: " + attributeMap.get("agility") + "\n" +
                "Health: " + health + "/" + healthXPLevel[playerLevel - 1];
    }

    public void getPlayerInventory() {
        inventory.displayInventory();
    }
}
