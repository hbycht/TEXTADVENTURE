package study.coco.ActionHandler;

import study.coco.Game;

import java.util.Arrays;

public class ExitHandler extends CommandHandler{

    private static final String[] phrases = {"exit", "quit", "q", "x"};
    private static final String message = "Continue the game...";           // Message if the player don't leave the game.
    private static final String exitMessage = "Bye, bye! See you soon!";    // Message if the player really exits the game.
    private static final String type = "exit";

    public ExitHandler(Game game) {
        super(game, type, phrases, message);
    }

    @Override
    public void handle() {
        super.handle();

        // show exit confirmation message
        String lineInput = this.game.getLineInput("Do you really want to quit?\n(Type \"Y\" to confirm.)\n >> ");
        // check if the user inputs "y" or "yes". (Add more phrases if needed.)
        String[] confirms = new String[]{"y","yes"};
        if(Arrays.asList(confirms).contains(lineInput)) {
            // finish the game & set the exit message
            this.game.finish();
            this.setMessage(exitMessage);
        }
    }
}
