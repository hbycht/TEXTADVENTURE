package study.coco.Game;

import java.util.Locale;

public enum Direction {
    NORTH(0, "north"),
    EAST(1, "east"),
    SOUTH(2, "south"),
    WEST(3, "west");

    private int dirInt;
    private String dirString;

    Direction(int dirInt, String dirString) {
        this.dirInt = dirInt;
        this.dirString = dirString;
    }

    public int asIndex(){
        return this.dirInt;
    }

    public String asString(){
        return this.dirString.toUpperCase(Locale.ROOT);
    }
}
