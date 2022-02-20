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
    private String noItemsMsg = "I can't see any items.";

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
        Inventory inv = this.inventory();

        if(inv.itemCount() > 0){
            String returnString = "";
            
            // return items the player can see
            returnString += String.format(itemOverviewMsg, inv.itemCount(), inv.itemCount() != 1 ? "s" : "") + "\n";
            returnString += this.inventory().listItems();
            
            return returnString;
        } 
        else {
            return noItemsMsg;
        }
    }

    public Inventory inventory() {
        return inventory;
    }
}
