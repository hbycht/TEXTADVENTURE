package study.coco.ActionHandler;

import study.coco.Game;
import study.coco.Item;

/**
 * A template to pre-build CommandHandler
 */
public class TakeHandler extends CommandHandler {

    private static final String[] phrases = {"t", "take"};
    private static final String takeMsg = "%s is now in your inventory.";
    private static final String errorMsg = "There is no item \"%s\"... Did you spell it right?";
    private static final String type = "take";

    public TakeHandler(Game game) {
        super(game, type, phrases, "");
    }

    @Override
    public void handle() {
        super.handle();

        Item object = checkForItem(this.game.getInputObject());

        if(object != null){
            this.game.player().takeItem(object);
            this.setMessage(String.format(takeMsg, object.getName()));
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
