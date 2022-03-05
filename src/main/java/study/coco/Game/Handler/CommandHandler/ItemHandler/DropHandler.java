package study.coco.Game.Handler.CommandHandler.ItemHandler;

import study.coco.Game.Game;
import study.coco.Game.Describable.Item;
import study.coco.Game.Describable.Location;

/**
 * This Handler reacts to drop commands.
 */
public class DropHandler extends ItemHandler {

    private static final String[] commands = {"d", "drop"};
    private static final String type = "drop";

    private static final String dropMsg = "Das Item <%s> liegt nun %s.";
    private static final String errorMsg = "Es gibt kein Item '%s' in meinem Inventar... Habe ich es richtig geschrieben?";

    public DropHandler(Game game) {
        super(game, type, commands, "");
    }

    public void handleItem() {

        Item object = this.checkForItemInInventory(this.game.getInputObject());
        Location position = this.game.player().getPosition();

        // IF KEY ITEM IS IN INVENTORY
        if(object != null){
            this.game.player().dropItem(object);
            this.setMessage(String.format(dropMsg, object.getName().toUpperCase(), position.getPreposition() + " " + position.getName().toUpperCase()));
        } else
            this.setMessage(String.format(errorMsg, this.game.getInputObject().toUpperCase()));
    }
}
