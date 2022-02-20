package study.coco.ActionHandler;

import study.coco.Game;
import study.coco.Item;

/**
 * This Handler reacts to take commands.
 */
public class TakeHandler extends CommandHandler {

    private static final String[] commands = {"t", "take"};
    private static final String takeMsg = "%s is now in my inventory.";
    private static final String errorMsg = "There is no item \"%s\"... Did I spell it right?";
    private static final String type = "take";

    public TakeHandler(Game game) {
        super(game, type, commands, "");
    }

    @Override
    public void handle() {
        super.handle();

        Item object = checkForItem(this.game.getInputObject());

        if(object != null){
            this.game.player().takeItem(object);
            this.setMessage(String.format(takeMsg, object.getName().toUpperCase()));
        } else {
            this.setMessage(String.format(errorMsg, this.game.getInputObject().toUpperCase()));
        }
    }

    private Item checkForItem(String object){
        for (Item item : this.game.player().getPosition().inventory().getItems()) {
            if (item.getName().equalsIgnoreCase(object))
                return item;
        }
        return null;
    }
}
