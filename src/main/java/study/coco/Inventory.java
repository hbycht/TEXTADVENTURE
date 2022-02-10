package study.coco;

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

    // remove item by item id
    public void removeItem(String id) {
        for(Item item : this.items){
            if(item.getId().equals(id)){
                this.items.remove(item);
                return;
            }
        }
    }

    // show all item descriptions
    public String listItems() {
        int itemCount = this.items.size();

        String names = "";

        // if there are items
        if(itemCount > 0){
            for (int i = 0; i < itemCount; i++){
                names += this.items.get(i).getName();
                if(i < itemCount - 2)
                    names += ", "; // add a comma to every item BUT the last
                else if(i < itemCount - 1)
                    names += " & "; // add a '&' to every item BUT the last
            }
        }
        return names.toUpperCase();
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
