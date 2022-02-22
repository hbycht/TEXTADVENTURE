package study.coco.Game.Handler.CommandHandler.ItemHandler;

import study.coco.Game.Game;
import study.coco.Game.Handler.CommandHandler.CommandHandler;
import study.coco.Game.Describable.Item;

/**
 * This Handler can do something with items.
 */
public abstract class ItemHandler extends CommandHandler {

    private static final String noItemMsg = "I have to specify an <ITEM> to %s it.";

    public ItemHandler(Game game, String type, String[] commands, String message) {
        super(game, type, commands, message);
    }

    @Override
    public void handle() {
        super.handle();

        // CHECK IF THE USER HAS GIVEN AN OBJECT
        if(!this.game.getInputObject().isBlank())
            this.handleItem();
        else
            this.setMessage(String.format(noItemMsg, this.getType().toUpperCase()));
    }

    /**
     * This methode contains the item handle instructions.
     * It will be executed if the player puts an object.
     */
    public abstract void handleItem();

    /**
     * Returns the Item with the given item name if it exists in players inventory.
     * @param itemName The name of the Item.
     * @return {@code Item} that has the given item name.
     */
    protected Item checkForItemInInventory(String itemName){
        for (Item item : this.game.player().inventory().getItems()) {
            if (item.getName().equalsIgnoreCase(itemName))
                return item;
        }
        return null;
    }

    /**
     * Returns the Item with the given item name if it exists in players actual location.
     * @param itemName The name of the Item.
     * @return {@code Item} that has the given item name.
     */
    protected Item checkForItemInLocation(String itemName){
        for (Item item : this.game.player().getPosition().inventory().getItems()) {
            if (item.getName().equalsIgnoreCase(itemName))
                return item;
        }
        return null;
    }
}
