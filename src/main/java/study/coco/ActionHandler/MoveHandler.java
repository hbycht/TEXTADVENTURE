package study.coco.ActionHandler;

import study.coco.Direction;
import study.coco.Game;
import study.coco.Location;
import study.coco.Player;

/**
 * A template to pre-build CommandHandler
 */
public class MoveHandler extends CommandHandler {

    private static final String[] phrases = {"north", "south", "east", "west", "n", "s", "e", "w"};
    private static final String defaultMsg = "Let's see what we get here.";
    private static final String errorMsg = "From here I can't go %s.";
    private static final String stepMsg = "I went %s.\n";
    private static final String type = "move";
    private Direction direction;

    public MoveHandler(Game game) {
        super(game, type, phrases, defaultMsg);
    }

    @Override
    public void handle() {
        super.handle();

        // Update input direction
        this.getDirection();

        // Get player & his location
        Player player = this.game.player();
        // Get directed Location
        Location directedLocation = player.getPosition().getGates()[this.direction.asIndex()];

        if(directedLocation != null){
            player.setPosition(directedLocation);
            System.out.println(String.format(stepMsg, this.direction.asString()));
            this.setMessage(defaultMsg);
        } else {
            this.setMessage(String.format(errorMsg, this.direction.asString()));
        }
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
