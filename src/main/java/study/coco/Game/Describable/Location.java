package study.coco.Game.Describable;

import study.coco.Game.Direction;
import study.coco.Game.Inventory;

public class Location extends Describable {
    private final String preposition;
    private Gate[] gates;
    private Inventory inventory;

    private final String locationNameMsg = "I'm now %s %s.";
    private String itemOverviewMsg = "I can see %d item%s:";
    private String noItemsMsg = "I can't see any items.";
    private final String dirDescriptionMsg = "In the %s I can see a %s.";


    public Location(String name, String preposition, String description) {
        super(name, description);
        this.preposition = preposition;
        this.gates = new Gate[4];
        this.inventory = new Inventory();
    }

    public void setGates(Gate north, Gate east, Gate south, Gate west) {
        this.gates[0] = north;
        this.gates[1] = east;
        this.gates[2] = south;
        this.gates[3] = west;

        // automatically set gate endings
        if(north != null)
            north.setEnding(this);
        if(south != null)
            south.setEnding(this);
        if(east != null)
            east.setEnding(this);
        if(west != null)
            west.setEnding(this);
    }

    public Gate[] getGates() {
        return gates;
    }
    public String getPreposition() {
        return preposition;
    }

    public String getFullDescription(){
        return
                // Location name
                String.format(locationNameMsg, this.getPreposition(), this.getName().toUpperCase()) + "\n" +
                // Location description
                this.getDescription() + "\n" +
                // Direction overview
                this.getDirectionOverview() +
                // List of items in location
                this.getItemOverview();
    }

    public String getDirectionOverview() {
        String dirOverview = "";

        for(int i = 0; i < this.gates.length; i++){
            if(this.gates[i] != null)
                dirOverview += String.format(dirDescriptionMsg, Direction.values()[i].asString(), this.gates[i].getName().toUpperCase()) + "\n";
        }
        return dirOverview;
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
