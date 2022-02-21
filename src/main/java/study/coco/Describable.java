package study.coco;

public abstract class Describable {
    private final String name;
    private final String description;

    public Describable(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
