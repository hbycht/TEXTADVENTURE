package study.coco.Game.Handler.CommandHandler.ItemHandler;

import study.coco.Game.Game;
import study.coco.Game.Describable.Item;

/**
 * This Handler reacts to take commands.
 */
public class TakeHandler extends ItemHandler {

    private static final String[] commands = {"t", "take"};
    private static final String type = "take";

    private static final String takeMsg = "Der Gegenstand <%s> ist nun in meinem Inventar.";
    private static final String errorMsg = "Es gibt kein Item '%s' an diesem Ort... Habe ich es richtig geschrieben?";

    public TakeHandler(Game game) {
        super(game, type, commands, "");
    }

    public void handleItem() {

        Item object = this.checkForItemInLocation(this.game.getInputObject());

        // IF KEY ITEM IS IN LOCATION
        if(object != null){
            this.game.player().takeItem(object);
            this.setMessage(String.format(takeMsg, object.getName().toUpperCase()));
        } else
            this.setMessage(String.format(errorMsg, this.game.getInputObject().toUpperCase()));
    }
}
