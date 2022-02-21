package study.coco.ActionHandler;

import study.coco.*;

/**
 * This Handler reacts to move commands.
 */
public class MoveHandler extends CommandHandler {

    private static final String[] commands = {"north", "south", "east", "west", "n", "s", "e", "w"};
    private static final String LetsSeeMsg = "Let's see what I get here...\n\n";
    private static final String errorMsg = "From here I can't go %s.";
    private static final String stepMsg = "I went %s.\n";
    private static final String type = "move";
    private Direction direction;

    public MoveHandler(Game game) {
        super(game, type, commands, LetsSeeMsg);
    }

    @Override
    public void handle() {
        super.handle();

        // Message to print
        String message = "";

        // Update input direction
        this.getDirection();

        // Get player & his directed location
        Player player = this.game.player();
        Gate directedGate = player.getPosition().getGates()[this.direction.asIndex()];

        // CHECK IF DIRECTION EXISTS
        if(directedGate != null){
            // CHANGE LOCATION
            player.setPosition(directedGate.getLocationBehind(player.getPosition()));
            message += String.format(stepMsg, this.direction.asString());
            message += LetsSeeMsg;
            message += player.getPosition().getFullDescription();
        }
        // CAN'T CHANGE LOCATION
        else {
            message = String.format(errorMsg, this.direction.asString());
        }

        this.setMessage(message);
//        System.out.println(message);
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
