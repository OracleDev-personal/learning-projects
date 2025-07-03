package characters;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static game.ExperiencePoints.healthXPLevel;

public class Enemy extends Entity {

    int attributePoints;
    int enemyLevel;
    Random random = new Random();

    public Enemy(String name, String race, int maxLevel) {
        super(name);
        enemyLevel = random.nextInt(1, maxLevel + 1);
        maxHealth = healthXPLevel[enemyLevel - 1];
        attributePoints = enemyLevel - 1;
        attributeMap = new HashMap<>(Map.of(
                "vitality", 0,
                "strength", 0,
                "agility", 0
        ));
        buildAttributes();
        health = 10 + (attributeMap.get("vitality") * 3);
    }

    void buildAttributes() {
        while(attributePoints > 1) {
            int attNum = random.nextInt();
            switch(attNum) {
                case 0 -> attributeMap.put("vitality", attributeMap.getOrDefault("vitality", 0) + 1);
                case 1 -> attributeMap.put("strength", attributeMap.getOrDefault("strength", 0) + 1);
                case 2 -> attributeMap.put("agility", attributeMap.getOrDefault("agility", 0) + 1);
            }
        }
    }
}
