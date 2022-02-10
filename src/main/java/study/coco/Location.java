package study.coco;

import java.sql.Array;
import java.util.ArrayList;

public class Location {
    private final String id;
    private final String name;
    private final String description;
    private Location[] gates;
    private Inventory inventory;

    public Location(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.gates = new Location[4];
        this.inventory = new Inventory();
    }

    public void setGates(Location north, Location east, Location south, Location west) {
        this.gates[0] = north;
        this.gates[1] = east;
        this.gates[2] = south;
        this.gates[3] = west;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Inventory inventory() {
        return inventory;
    }
}
