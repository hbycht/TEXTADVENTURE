package study.coco.Game.Describable;

public class Item extends Describable{
    private final String type;

    public Item(String name, String description, String type) {
        super(name, description);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
