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

    private static final String tryMsg = "I try to use '%s'...\n";
    private static final String noGateErrorMsg = "... but I don't know which gate I should open with.\nI should go in one direction first.";
    private static final String noItemErrorMsg = "... but there is no item '%s' in my inventory... Did I spell it right?";

    public UseHandler(Game game) {
        super(game, type, commands, "");
    }

    public void handleItem() {

        String inputObj = this.game.getInputObject();
        Item key = this.checkForItemInInventory(inputObj);
        Location position = this.game.player().getPosition();
        Gate actualGate = this.game.player().getActualGate();

        String message = String.format(tryMsg, inputObj);

//        // IF KEY ITEM IS IN INVENTORY
//        if(key != null)
//            // IF PLAYER LOOKS AT A GATE RIGHT NOW
//            if(actualGate != null)
//                this.setMessage(actualGate.open(key));
//            else
//                this.setMessage(noGateErrorMsg);
//        else{
////            if(actualGate.getKeyword().equals(inputObj))
//            this.setMessage(String.format(noItemErrorMsg, this.game.getInputObject().toUpperCase()));
//        }

        // IF PLAYER LOOKS AT A GATE RIGHT NOW
        if(actualGate != null){
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
