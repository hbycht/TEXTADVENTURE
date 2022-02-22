package study.coco.Game.Handler.CommandHandler;

import study.coco.Game.Game;
import study.coco.Game.Handler.Handler;

/**
 * With the CommandHandler you can build every Handler which listens to a command on-the-fly.
 * Just give it some phrases and a reply message.
 */
public abstract class CommandHandler extends Handler {

    private final String[] commands;

    public CommandHandler(Game game, String type, String[] commands, String message) {
        super(game, type);
        this.commands = commands;
        this.message = message;
    }

    @Override
    public boolean matches(String command) {
        for (String phrase : this.getCommands()) {
            // IF THE INPUT COMMAND MATCHES THE HANDLER COMMAND
            if (phrase.equalsIgnoreCase(command))
                return true;
        }
        return false;
    }

    @Override
    public void handle() {

    }

    protected String[] getCommands() {
        return this.commands;
    }

}
