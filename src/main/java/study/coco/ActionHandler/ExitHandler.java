package study.coco.ActionHandler;

import study.coco.Game;

public class ExitHandler extends CommandHandler{

    public ExitHandler(Game game) {
        super(game);
        this.phrases = new String[]{"exit", "quit", "q", "x"};
        this.message = "Bye, bye! See you soon!";
    }

    @Override
    protected String[] getPhrases() {
        return this.phrases;
    }

    @Override
    public String handle() {
        this.game.exit();

        return super.handle();
    }
}
