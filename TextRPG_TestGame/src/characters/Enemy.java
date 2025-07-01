package characters;

import items.Inventory;

import java.util.HashMap;
import java.util.Map;

import static game.ExperiencePoints.healthXPLevel;

public class Enemy extends Character {

    int attributePoints;
    int enemyLevel;

    public Enemy(String name, String race, int level) {
        super(name);
        race = race;
        maxHealth = healthXPLevel[enemyLevel - 1];
        health = 10 + (attributeMap.get("vitality") * 3);
        enemyLevel = 1;
        attributePoints = enemyLevel - 1;
        attributeMap = new HashMap<>(Map.of(
                "vitality", 0,
                "strength", 0,
                "agility", 0
        ));
    }
}
