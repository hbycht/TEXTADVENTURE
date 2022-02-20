package study.coco.ActionHandler;

import study.coco.Game;
import study.coco.Inventory;

/**
 * This Handler reacts to inventory commands.
 */
public class InventoryHandler extends CommandHandler {

    private static final String[] commands = {"i", "inv", "inventory"};
    private static final String message = "";
    private static final String inventoryMsg = "INVENTORY [%d item%s]:\n%s";
    private String noItemsMsg = "I haven't any items in my inventory.";
    private static final String type = "default";

    public InventoryHandler(Game game) {
        super(game, type, commands, message);
    }

    @Override
    public void handle() {
        super.handle();

        Inventory inv = this.game.player().inventory();

        this.setMessage(String.format(inventoryMsg, inv.itemCount(), inv.itemCount() != 1 ? "s" : "", inv.listItems()));
    }
}
