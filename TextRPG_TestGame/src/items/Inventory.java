package items;

import java.util.ArrayList;

public class Inventory {

    private final ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void displayInventory() {
        System.out.println("\n=== | Inventory | ===\n");
        for(Item item : items) {
            System.out.println(item.toString());
        }
    }
}
