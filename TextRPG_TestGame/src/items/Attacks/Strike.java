package items.Attacks;

import items.Weapon;

public class Strike extends Attack {

    public Strike(Weapon weapon) {
        super("Sword Strike", weapon.getDamage(), 80);
    }
}
