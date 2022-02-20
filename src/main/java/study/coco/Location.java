package study.coco;

import java.util.ArrayList;

public class Location {
    private final String id;
    private final String name;
    private final String preposition;
    private final String description;
    private Location[] gates;
    private Inventory inventory;

    private String locationNameMsg = "I'm now %s %s.";
    private String itemOverviewMsg = "I can see %d item%s:";

    public Location(String id, String name, String preposition, String description) {
        this.id = id;
        this.name = name;
        this.preposition = preposition;
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

    public Location[] getGates() {
        return gates;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPreposition() {
        return preposition;
    }

    public String getDescription() {
        return description;
    }

    public String getFullDescription(){
        return
                // Location name
                String.format(locationNameMsg, this.getPreposition(), this.getName().toUpperCase()) + "\n" +
                // Location description
                this.description + "\n" +
                // List of items in location
                this.getItemOverview();
    }

    public String getItemOverview() {
        String returnString = "";

        ArrayList<Item> items = this.inventory().getItems();

        if(items.size() > 0){
            // return items the player can see
            returnString += String.format(itemOverviewMsg, items.size(), items.size() != 1 ? "s" : "") + "\n";
            returnString += this.inventory().listItems();
        } else {
            return "I can't see any items.";
        }
        return returnString;
    }

    public Inventory inventory() {
        return inventory;
    }
}
