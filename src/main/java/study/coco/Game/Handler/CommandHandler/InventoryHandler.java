package study.coco.Game.Handler.CommandHandler;

import study.coco.Game.Game;
import study.coco.Game.Inventory;

/**
 * This Handler reacts to inventory commands.
 */
public class InventoryHandler extends CommandHandler {

    private static final String[] commands = {"i", "inv", "inventory"};
    private static final String type = "inventory";

    private static final String inventoryMsg = "My INVENTORY [%d item%s]:\n%s";
    private String noItemsMsg = "My INVENTORY is empty!";

    public InventoryHandler(Game game) {
        super(game, type, commands, "");
    }

    @Override
    public void handle() {
        super.handle();

        Inventory inv = this.game.player().inventory();

        // IF THERE ARE ITEMS IN THE INVENTORY
        if(inv.itemCount() > 0)
            this.setMessage(String.format(inventoryMsg, inv.itemCount(), inv.itemCount() != 1 ? "s" : "", inv.listItems()));
        else
            this.setMessage(noItemsMsg);
    }
}
