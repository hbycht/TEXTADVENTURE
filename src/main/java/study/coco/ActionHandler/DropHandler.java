package study.coco.ActionHandler;

import study.coco.Game;
import study.coco.Item;
import study.coco.Location;

/**
 * This Handler reacts to drop commands.
 */
public class DropHandler extends ItemHandler {

    private static final String[] commands = {"d", "drop"};
    private static final String type = "drop";

    private static final String dropMsg = "The %s is now %s.";
    private static final String errorMsg = "There is no item \"%s\" in my inventory... Did I spell it right?";

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
