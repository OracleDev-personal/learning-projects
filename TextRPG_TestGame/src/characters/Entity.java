package characters;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class Entity {

    private Random random = new Random();

    private final String name;
    public String race;
    public int health;
    public int maxHealth;
    public Map<String, Integer> attributeMap = new HashMap<>(Map.of(
            "vitality", 0, // vitality point = 3 hp
            "strength", 0, // strength point = 1 damage (1 physical power)
            "agility", 0 // agility point = 1 speed && 1% accuracy
    ));
    int armorRating;
    int physicalPower;
    int speed;


    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", race='" + race + '\'' +
                ", health=" + health +
                ", maxHealth=" + maxHealth +
                ", armorRating=" + armorRating +
                ", physicalPower=" + physicalPower +
                ", speed=" + speed +
                '}';
    }

    public int getSpeed() {
        speed = random.nextInt(1, 11) + attributeMap.get("agility");
        return speed;
    }
}
