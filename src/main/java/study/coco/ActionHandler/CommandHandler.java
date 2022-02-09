package study.coco.ActionHandler;

import study.coco.Game;

import java.util.ArrayList;

/**
 * With the CommandHandler you can build every Handler which listens to a command on-the-fly.
 * Just give it some phrases and a reply message.
 */
public class CommandHandler extends Handler {

    protected String command;
    protected String[] phrases;


    public CommandHandler(Game game, String type) {
        super(game, type);
        this.phrases = new String[]{""};
        this.message = "Default output of CommandHandler. You can overwrite it.";
    }

    public CommandHandler(Game game, String type, String[] phrases, String message) {
        super(game, type);
        this.phrases = phrases;
        this.message = message;
    }

    @Override
    public boolean matches() {
        this.command = this.game.getLineInput();
        for (String phrase : this.getPhrases()) {
            if (phrase.equalsIgnoreCase(this.command))
                return true;
        }
        return false;
    }

    protected String[] getPhrases() {
        return this.phrases;
    }

//    public String getMessage() {
//        return this.message;
//    }
}
