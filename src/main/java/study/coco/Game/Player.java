package study.coco.Game;

import study.coco.Game.Describable.Gate;
import study.coco.Game.Describable.Item;
import study.coco.Game.Describable.Location;

public class Player {
    private Location position;
    private Gate actualGate;
    private Inventory inventory;

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

    public void setActualGate(Gate actualGate) {
        this.actualGate = actualGate;
    }

    public Location getPosition() {
        return position;
    }

    public Gate getActualGate() {
        return actualGate;
    }

    public Inventory inventory() {
        return inventory;
    }
}
