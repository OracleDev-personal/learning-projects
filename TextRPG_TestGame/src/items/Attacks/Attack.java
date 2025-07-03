package items.Attacks;

import characters.Entity;

import java.util.Random;

public class Attack {

    final String name;
    final int baseDamage;
    private int accuracy;

    public Attack(String name, int baseDamage, int accuracy) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    boolean checkAccuracy(Entity user) {
        Random random = new Random();
        accuracy += user.getSpeed();
        if(accuracy < 100 && accuracy < random.nextInt(1, 101)) {
            return false;
        }
        else {
            return true;
        }
    }

    public void attack(Entity user, Entity target) {
        System.out.printf(user.getName() + " is using " + name + "!");
        if(checkAccuracy(user)) {
            int totalDamage = baseDamage + user.getStrength();
            target.takeDamage(totalDamage);
        }
        else {
            System.out.println("Attack missed!");
        }

    }


}
