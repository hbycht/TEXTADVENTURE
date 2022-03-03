package study.coco.Game;

import study.coco.Game.Describable.Gate;
import study.coco.Game.Describable.Item;
import study.coco.Game.Describable.Location;

public class Player {
    private Location position;
    private Gate actualGate;
    private Inventory inventory;
    private Item actualItem;

    public Player(Location position) {
        this.position = position;
        this.inventory = new Inventory();
    }

    /**
     * Take the item from location & add to players inventory.
     * @param item element to be added to the players inventory.
     */
    public void takeItem(Item item){
        this.inventory.addItem(item);
        this.position.inventory().removeItem(item);
    }

    /**
     * Drop the item into location & remove it from the inventory of the player.
     * @param item element to be dropped into current location
     */
    public void dropItem(Item item){
        this.inventory.removeItem(item);
        this.position.inventory().addItem(item);
    }

    public void setPosition(Location position) {
        this.position = position;
    }

    public Location getPosition() {
        return position;
    }

    public void setActualGate(Gate actualGate) {
        this.actualGate = actualGate;
    }

    public Gate getActualGate() {
        return actualGate;
    }

    public void setActualItem(Item actualItem) {
        this.actualItem = actualItem;
    }

    public Item getActualItem() {
        return actualItem;
    }

    public Inventory inventory() {
        return inventory;
    }
}
