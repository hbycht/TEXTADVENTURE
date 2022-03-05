package study.coco.Game.Describable;

import java.util.Objects;

public class Gate extends Describable {
    private final Location[] endings;
    private final Item key;
    private final String keyword;
    private boolean isOpen;

    private final String stillOpenMsg = "... aber der Durchgang %s ist schon offen. Ich kann ihn passieren.";
    private final String rightKeyMsg = "... Zack! Der Durchgang %s hat sich geöffnet. Ich kann ihn nun durchqueren.";
    private final String wrongKeyMsg = "... allerdings kann ich den Durchgang %s nicht mit dem Gegenstand <%s>.";
    private final String wrongKeywordMsg = "... allerdings ist '%s' nicht das richtige Passwort, um den Durchgang %s zu öffnen.";

    /**
     * Constructor for an OPEN gate
     * @param name of the gate. It appears when the player trys to pass the gate.
     * @param description of the gate. It appears when the player looks at the gate.
     */
    public Gate(String name, String description) {
        super(name, description);
        this.endings = new Location[2];
        this.key = null;
        this.keyword = ".";
        this.isOpen = true;
    }

    /**
     * Constructor for a CLOSED gate per KEY.
     * @param name of the gate. It appears when the player trys to pass the gate.
     * @param description of the gate. It appears when the player looks at the gate.
     * @param key for the gate. {@code Item} the player have to use to open the gate.
     */
    public Gate(String name, String description, Item key) {
        super(name, description);
        this.endings = new Location[2];
        this.key = key;
        this.keyword = ".";
        this.isOpen = false;
    }

    /**
     * Constructor for a CLOSED gate per KEYWORD.
     * @param name of the gate. It appears when the player trys to pass the gate.
     * @param description of the gate. It appears when the player looks at the gate.
     * @param keyword for the gate. {@code String} the player types in to open the gate.
     */
    public Gate(String name, String description, String keyword) {
        super(name, description);
        this.endings = new Location[2];
        this.key = null;
        this.keyword = keyword;
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
        if(!isOpen)
            if(key == this.key){
                isOpen = true;
                return String.format(rightKeyMsg, this.getName().toUpperCase());
            }
            else
                return String.format(wrongKeyMsg, this.getName().toUpperCase(), key.getName().toUpperCase());
        else
            return String.format(stillOpenMsg, this.getName().toUpperCase());
    }

    /**
     * Opens the gate if the player uses the right keyword.
     * @param keyword {@code String} the player tries to open the gate with.
     * @return Returns the representative message {@code String}.
     */
    public String open(String keyword) {
        if(!this.isOpen)
            if(this.keyword.equalsIgnoreCase(keyword)){
                this.isOpen = true;
                return String.format(rightKeyMsg, this.getName().toUpperCase());
            }
            else
                return String.format(wrongKeywordMsg, keyword.toUpperCase(), this.getName().toUpperCase());
        else
            return String.format(stillOpenMsg, this.getName().toUpperCase());
    }

    /**
     * Getter for the open/closed status of the gate.
     * @return Returns true if the gate is open.
     */
    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean hasKey(){
        return this.key != null;
    }

    public boolean hasKeyword(){
        return !this.keyword.equalsIgnoreCase(".");
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

    @Override
    public String getDescription() {
        return super.getDescription() + " " + (this.isOpen ? "It is open." : "It is closed.");
    }

    public String getKeyword() {
        return keyword;
    }
}
