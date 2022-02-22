package study.coco.Game.Handler.CommandHandler;

import study.coco.Game.Direction;
import study.coco.Game.Game;
import study.coco.Game.Describable.Gate;
import study.coco.Game.Player;

/**
 * This Handler reacts to move commands.
 */
public class MoveHandler extends CommandHandler {

    private static final String[] commands = {"north", "south", "east", "west", "n", "s", "e", "w"};
    private static final String type = "move";
    private Direction direction;

    private static final String LetsSeeMsg = "Let's see what I get here...\n\n";
    private static final String errorMsg = "From here I can't go %s.";
    private static final String stepMsg = "I went %s.\n";
    private static final String tryStepMsg = "I tried to go %s...\n";
    private static final String closedGateMsg = "The %s is closed. Is here a key somewhere?";

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
            // CHECK IF THE GATE IS PASSABLE
            if(directedGate.isOpen()){
                // CHANGE LOCATION
                player.setPosition(directedGate.getLocationBehind(player.getPosition()));
                player.setActualGate(null);
                message += String.format(stepMsg, this.direction.asString());
                message += LetsSeeMsg;
                message += player.getPosition().getFullDescription();
            }
            else {
                // PRINT MESSAGE THAT THE PLAYER CAN'T PASS THE CLOSED GATE
                this.game.player().setActualGate(directedGate);
                message += String.format(tryStepMsg, this.direction.asString());
                message += String.format(closedGateMsg, directedGate.getName());
            }

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