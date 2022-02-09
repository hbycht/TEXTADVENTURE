package study.coco;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

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
        String descriptions = "";
        for (Item item : this.items){
            descriptions += item.getDescription() + "\n";
        }
        return descriptions;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
