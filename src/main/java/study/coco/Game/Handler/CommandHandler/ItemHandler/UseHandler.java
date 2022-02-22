package study.coco.Game.Handler.CommandHandler.ItemHandler;

import study.coco.Game.Game;
import study.coco.Game.Describable.Gate;
import study.coco.Game.Describable.Item;
import study.coco.Game.Describable.Location;

/**
 * This Handler reacts to use commands.
 */
public class UseHandler extends ItemHandler {

    private static final String[] commands = {"u", "use"};
    private static final String type = "use";

    private static final String tryMsg = "I try to use %s...";
    private static final String noGateErrorMsg = "... but I don't know which gate I should open with.\nI should go in one direction first.";
    private static final String noItemErrorMsg = "There is no item \"%s\" in my inventory... Did I spell it right?";

    public UseHandler(Game game) {
        super(game, type, commands, "");
    }

    public void handleItem() {

        Item key = this.checkForItemInInventory(this.game.getInputObject());
        Location position = this.game.player().getPosition();
        Gate actualGate = this.game.player().getActualGate();

        // IF KEY ITEM IS IN INVENTORY
        if(key != null)
            // IF PLAYER LOOKS AT A GATE RIGHT NOW
            if(actualGate != null)
                this.setMessage(actualGate.open(key));
            else
                this.setMessage(noGateErrorMsg);
        else
            this.setMessage(String.format(noItemErrorMsg, this.game.getInputObject().toUpperCase()));

    }
}
