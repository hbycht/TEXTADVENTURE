package study.coco.Game;

import java.util.Locale;

public enum Direction {
    NORTH(0, "norden"),
    EAST(1, "osten"),
    SOUTH(2, "süden"),
    WEST(3, "westen");

    private int dirInt;
    private String dirString;
    private char dirChar;

    Direction(int dirInt, String dirString) {
        this.dirInt = dirInt;
        this.dirString = dirString;
        this.dirChar = dirString.charAt(0);
    }

    public int asIndex(){
        return this.dirInt;
    }

    public String asString(){
        return this.dirString.toUpperCase(Locale.ROOT);
    }

    public char asChar() {
        return dirChar;
    }
}
