package characters;

import java.util.Map;
import java.util.HashMap;

public class Character {

    private String name;
    public String race;
    public int health;
    public int maxHealth;
    public Map<String, Integer> attributeMap = new HashMap<>(Map.of(
            "vitality", 0, // vitality point = 3 hp
            "strength", 0, // strength point = 1 damage (1 physical power)
            "agility", 0 // agility pont = 5 speed
    ));
    int armorRating;
    int physicalPower;
    int speed;


    public Character(String name) {
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
}
