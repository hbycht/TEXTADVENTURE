package study.coco.ActionHandler;

import study.coco.Game;

/**
 * With the CommandHandler you can build every Handler which listens to a command on-the-fly.
 * Just give it some phrases and a reply message.
 */
public class CommandHandler extends Handler {

    private final String[] commands;

    public CommandHandler(Game game, String type, String[] commands, String message) {
        super(game, type);
        this.commands = commands;
        this.message = message;
    }

    @Override
    public boolean matches(String command) {
        for (String phrase : this.getCommands()) {
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
