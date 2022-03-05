package study.coco.Game.Handler.CommandHandler;

import study.coco.Game.Game;

import java.util.Arrays;

/**
 * This Handler reacts to exit commands.
 */
public class ExitHandler extends CommandHandler{

    private static final String[] commands = {"exit", "quit", "q", "x"};
    private static final String message = "Dann geht es wohl weiter...";           // Message if the player don't leave the game.
    private static final String exitMessage = "Tschüüüüss! Auf Wiedersehen!";    // Message if the player really exits the game.
    private static final String type = "exit";

    public ExitHandler(Game game) {
        super(game, type, commands, message);
    }

    @Override
    public void handle() {
        super.handle();

        // show exit confirmation message
        String lineInput = this.game.getLineInput("\nWill ich das Spiel wirklich beenden?\n(Schreib' \"Y\" zur Bestätigung.)\n >> ");
        // check if the user inputs "y" or "yes". (Add more phrases if needed.)
        String[] confirms = new String[]{"y","yes"};
        if(Arrays.asList(confirms).contains(lineInput)) {
            // finish the game & set the exit message
            this.game.finish();
            this.setMessage(exitMessage);
        }
    }
}
