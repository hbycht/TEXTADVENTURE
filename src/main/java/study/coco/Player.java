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
