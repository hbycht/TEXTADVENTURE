package study.coco.Game.Handler.CommandHandler;

import study.coco.Game.Game;

import java.util.Arrays;

/**
 * This Handler reacts to exit commands.
 */
public class ExitHandler extends CommandHandler{

    private static final String[] commands = {"exit", "quit", "q", "x"};
    private static final String message = "Continue the game...";           // Message if the player don't leave the game.
    private static final String exitMessage = "Bye, bye! See you soon!";    // Message if the player really exits the game.
    private static final String type = "exit";

    public ExitHandler(Game game) {
        super(game, type, commands, message);
    }

    @Override
    public void handle() {
        super.handle();

        // show exit confirmation message
        String lineInput = this.game.getLineInput("\nDo I really want to quit?\n(Type \"Y\" to confirm.)\n >> ");
        // check if the user inputs "y" or "yes". (Add more phrases if needed.)
        String[] confirms = new String[]{"y","yes"};
        if(Arrays.asList(confirms).contains(lineInput)) {
            // finish the game & set the exit message
            this.game.finish();
            this.setMessage(exitMessage);
        }
    }
}
