package study.coco;

public class Gate extends Describable{
    private Location[] endings;
    private Item key;
    private boolean isOpen;

    public Gate(String name, String description, Item key) {
        super(name, description);
        this.endings = new Location[2];
//        this.endings[0] = ending1;
//        this.endings[1] = ending2;
        this.key = key;
        this.isOpen = true;
    }

    public void setEnding(Location ending) {
        if(this.endings[0] == null)
            this.endings[0] = ending;
        else
            this.endings[1] = ending;
    }

    public void open() {
        isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public Location getLocationBehind(Location actualPosition) {
        if(this.endings[0] == actualPosition)
            return this.endings[1];
        return this.endings[0];
    }
}
