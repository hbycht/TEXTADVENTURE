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

    private static final String tryMsg = "Ich versuche es mit dem '%s'...\n";
    private static final String noGateErrorMsg = "... allerdings weiß ich nicht, was ich damit öffnen soll.\nIch sollte erst in eine Richtung gehen.";
    private static final String noItemErrorMsg = "... allerdings gibt es keinen Gegenstand '%s' in meinem Inventar... Habe ich es richtig geschrieben?";

    public UseHandler(Game game) {
        super(game, type, commands, "");
    }

    public void handleItem() {

        String inputObj = this.game.getInputObject();
        Item key = this.checkForItemInInventory(inputObj);
        Location position = this.game.player().getPosition();
        Gate actualGate = this.game.player().getActualGate();
        Item actualItem = this.game.player().getActualItem();

        String message = String.format(tryMsg, inputObj);

        // IF PLAYER IS IN FINAL LOCATION & USES FINAL KEY
        if(position == this.game.finalLocation() && this.game.finalKey().getName().equalsIgnoreCase(inputObj)){
            // SOLVE THE GAME (Goal of the game)
            this.game.solve();
        }
        // IF PLAYER LOOKS AT A GATE RIGHT NOW
        else if(actualGate != null){
            // IF IT'S A KEY GATE
            if(actualGate.hasKey()){
                // IF KEY ITEM IS IN INVENTORY
                if(key != null)
                    message += actualGate.open(key);
                else
                    message += String.format(noItemErrorMsg, this.game.getInputObject().toUpperCase());
            }
            // IF IT'S A KEYWORD GATE
            else if(actualGate.hasKeyword())
                message += actualGate.open(inputObj);
        }
        else
            message += noGateErrorMsg;

        this.setMessage(message);
    }
}
