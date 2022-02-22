package study.coco;

public class Gate extends Describable{
    private Location[] endings;
    private Item key;
    private boolean isOpen;

    private String wrongKeyMsg = "I can't open the %s with %s.";
    private String rightKeyMsg = "The %s opens and I can pass it now.";

    /**
     * Constructor for an OPEN gate
     * @param name of the gate. It appears when the player trys to pass the gate.
     * @param description of the gate. It appears when the player looks at the gate.
     */
    public Gate(String name, String description) {
        super(name, description);
        this.endings = new Location[2];
        this.key = null;
        this.isOpen = true;
    }

    /**
     * Constructor for a CLOSED gate.
     * @param name of the gate. It appears when the player trys to pass the gate.
     * @param description of the gate. It appears when the player looks at the gate.
     * @param key for the gate. {@code Item} the player have to use to open the gate.
     */
    public Gate(String name, String description, Item key) {
        super(name, description);
        this.endings = new Location[2];
        this.key = key;
        this.isOpen = false;
    }

    /**
     * Sets a free ending.
     * @param ending {@code Location} which is an ending of the gate.
     */
    public void setEnding(Location ending) {
        if(this.endings[0] == null)
            this.endings[0] = ending;
        else
            this.endings[1] = ending;
    }

    /**
     * Opens the gate if the player uses the right key.
     * @param key {@code Item} the player tries to open the gate with.
     * @return Returns the representative message {@code String}.
     */
    public String open(Item key) {
        if(key == this.key){
            isOpen = true;
            return String.format(rightKeyMsg, this.getName().toUpperCase());
        }
        else
            return String.format(wrongKeyMsg, this.getName().toUpperCase(), key.getName().toUpperCase());
    }

    /**
     * Getter for the open/closed status of the gate.
     * @return Returns true if the gate is open.
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Method to get the {@code Location} behind the gate representative of the actualPosition {@code Location}.
     * @param actualPosition Actual position {@code Location} of the player.
     * @return Returns {@code Location} of the other side of the gate.
     */
    public Location getLocationBehind(Location actualPosition) {
        if(this.endings[0] == actualPosition)
            return this.endings[1];
        return this.endings[0];
    }
}
