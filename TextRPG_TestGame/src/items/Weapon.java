package items;

public class Weapon extends Item {

    int damage;
    String quality;

    public Weapon(String name, int quantity, String quality) {
        super(name, quantity);
        this.quality = quality;
        switch (quality) {
            case "wood" -> this.damage = 2;
            case "iron" -> this.damage = 3;
            case "steel" -> this.damage = 5;
            default -> this.damage = 1;
        }
    }

    public String getQuality() {
        return quality;
    }

    @Override
    public String toString() {
        return "Item : " + getName() + " | Quantity: " + getQuantity() + " | Quality: " + getQuality();
    }
}
