package study.coco.ActionHandler;

import study.coco.Game;
import study.coco.Item;
import study.coco.Location;

/**
 * This Handler reacts to move commands.
 */
public class DropHandler extends CommandHandler {

    private static final String[] commands = {"d", "drop"};
    private static final String dropMsg = "The %s is now %s.";
    private static final String errorMsg = "There is no item \"%s\" in my inventory... Did I spell it right?";
    private static final String type = "drop";

    public DropHandler(Game game) {
        super(game, type, commands, "");
    }

    @Override
    public void handle() {
        super.handle();

        Item object = checkForItem(this.game.getInputObject());
        Location position = this.game.player().getPosition();

        if(object != null){
            this.game.player().dropItem(object);
            this.setMessage(String.format(dropMsg, object.getName().toUpperCase(), position.getPreposition() + " " + position.getName().toUpperCase()));
        } else {
            this.setMessage(String.format(errorMsg, this.game.getInputObject().toUpperCase()));
        }
    }

    /**
     * Returns the Item with the given item name if it exists.
     * @param itemName The name of the Item.
     * @return {@code Item} that has the given item name.
     */
    private Item checkForItem(String itemName){
        for (Item item : this.game.player().inventory().getItems()) {
            if (item.getName().equalsIgnoreCase(itemName))
                return item;
        }
        return null;
    }
}
