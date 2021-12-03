package study.coco.ActionHandler;

import study.coco.Game;


public abstract class CommandHandler extends Handler {

    protected String command;
    protected String[] phrases;
    protected String message;


    public CommandHandler(Game game) {
        super(game);
        this.phrases = new String[]{""};
    }

    @Override
    public boolean matches() {
        this.command = this.game.getLineInput();
        for (String phrase : this.getPhrases()) {
            if (phrase.equals(this.command)) {
                return true;
            }
        }
        return false;
    }

    public String handle() {
        return this.getMessage();
    }

    protected String[] getPhrases() {
        return this.phrases;
    }

    public String getMessage() {
        return this.message;
    }
}
