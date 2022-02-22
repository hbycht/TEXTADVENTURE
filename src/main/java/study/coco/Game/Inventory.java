package study.coco.Game;

import study.coco.Game.Describable.Item;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public Item addItem(Item item) {
        this.items.add(item);
        return item;
    }

    // remove item by giving the whole instance
    public void removeItem(Item item) {
        this.items.remove(item);
    }

    // show all item descriptions
    public String listItems() {
        String names = "";

        // if there are items
        if(this.itemCount() > 0){
            for (int i = 0; i < this.itemCount(); i++){
                names += this.items.get(i).getName();
                if(i < this.itemCount() - 2)
                    names += ", "; // add a comma to every item BUT the last
                else if(i < this.itemCount() - 1)
                    names += " & "; // add a '&' to every item BUT the last
            }
        }
        return names.toUpperCase();
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    
    public int itemCount() {
        return this.items.size();
    }
}
