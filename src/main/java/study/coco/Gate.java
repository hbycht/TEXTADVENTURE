package study.coco;

public class Gate extends Describable{
    private Location[] endings;
    private Item key;
    private boolean isOpen;

    public Gate(String name, String description, Location[] endings, Item key) {
        super(name, description);
        this.endings = endings;
        this.key = key;
        this.isOpen = false;
    }
}
