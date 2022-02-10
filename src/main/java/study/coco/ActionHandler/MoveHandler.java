package study.coco.ActionHandler;

import study.coco.Direction;
import study.coco.Game;

/**
 * A template to pre-build CommandHandler
 */
public class MoveHandler extends CommandHandler {

    private static final String[] phrases = {"north", "south", "east", "west", "n", "s", "e", "w"};
    private static final String message = "I went one step.\nLet's see what we get here.";
    private static final String tmpMessage = "I went %s.\nLet's see what we get here.";
    private static final String type = "move";
    private Direction direction;

    public MoveHandler(Game game) {
        super(game, type, phrases, message);
    }

    @Override
    public void handle() {
        super.handle();
        this.setMessage(String.format(tmpMessage, this.getDirection().asString()));
    }

    private Direction getDirection(){
        String lineInput = this.game.getLineInput();
        switch (lineInput){
            case "n": this.direction = Direction.NORTH; break;
            case "north": this.direction = Direction.NORTH; break;
            case "e": this.direction = Direction.EAST; break;
            case "east": this.direction = Direction.EAST; break;
            case "s": this.direction = Direction.SOUTH; break;
            case "south": this.direction = Direction.SOUTH; break;
            case "w": this.direction = Direction.WEST; break;
            case "west": this.direction = Direction.WEST; break;
        }
        return this.direction;
    }
}
