package study.coco.Game.Handler.CommandHandler;

import study.coco.Game.Describable.Describable;
import study.coco.Game.Describable.Gate;
import study.coco.Game.Describable.Item;
import study.coco.Game.Describable.Location;
import study.coco.Game.Direction;
import study.coco.Game.Game;
import study.coco.Game.Player;

/**
 * This Handler reacts to inventory commands.
 */
public class LookHandler extends CommandHandler {

    private static final String[] commands = {"l", "look"};
    private static final String type = "look";

    private static final String lookMsg = "Let me check this %s...\n%s";
    private String errorMsg = "I can't see a %s... Did I spell it right?";

    public LookHandler(Game game) {
        super(game, type, commands, "");
    }

    @Override
    public void handle() {
        super.handle();

        String object = this.game.getInputObject();
        Location actualLocation = this.game.player().getPosition();

        // debug
//        System.out.println("'" + object + "'");

        // GIVE LOCATION DESCRIPTION IF INPUT OBJECT IS EMPTY OR "AROUND"
        if(object.isBlank() || object.equals("around"))
            this.setMessage(actualLocation.getLookDescription());

        else{
            // GIVE DESCRIPTION OF THE INPUT OBJECT IF IT EXISTS
            Describable describable = this.getDescribable(object);
            if(describable != null)
                this.setMessage(String.format(lookMsg, describable.getName(), describable.getDescription()));
            // ELSE GIVE ERROR MESSAGE
            else
                this.setMessage(String.format(errorMsg, object));
        }
    }

    protected Describable getDescribable(String name){

        Player player = this.game.player();
        Location actualLocation = player.getPosition();
        Gate[] gates = actualLocation.getGates();

        // LOOK FOR ITEM IN PLAYERS INVENTORY
        for (Item item : player.inventory().getItems()) {
            if (item.getName().equalsIgnoreCase(name))
                return item;
        }

        // LOOK FOR ITEM IN LOCATION
        for (Item item : actualLocation.inventory().getItems()) {
            if (item.getName().equalsIgnoreCase(name))
                player.setActualItem(item);
                return item;
        }

        // LOOK FOR GATE IN LOCATION by gate name
        for (Gate gate : gates) {
            if (gate != null && gate.getName().equalsIgnoreCase(name)){
                player.setActualGate(gate); // Set the actual gate so the player can USE an item as key on it.
                return gate;
            }
        }

        // LOOK FOR GATE IN LOCATION by direction
        for (Direction dir : Direction.values()) {
            if (dir.asString().equalsIgnoreCase(name) || dir.asChar() == name.charAt(0)){
                player.setActualGate(gates[dir.asIndex()]); // Set the actual gate so the player can USE an item as key on it.
                return player.getActualGate();
            }
        }

        // IF NOTHING FOUND RETURN NULL
        return null;
    }
}
