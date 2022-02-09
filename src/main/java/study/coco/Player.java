package study.coco;

import java.util.ArrayList;

public class Player {
    private Location position;
//    private Lookable lookAt;
    private Inventory inventory;

    public Player(Location position) {
        this.position = position;
        this.inventory = new Inventory();
    }

    // change item holder from location to player
    public void takeItem(Item item){
        this.inventory.addItem(item);
        this.position.inventory().removeItem(item);
    }

    // change item holder from player to location
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

    public Inventory inventory() {
        return inventory;
    }
}
