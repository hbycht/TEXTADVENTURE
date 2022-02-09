package study.coco;

import java.util.ArrayList;

public class Location {
    private String id;
    private String name;
    private String description;
    private Inventory inventory;

    public Location(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.inventory = new Inventory();
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
