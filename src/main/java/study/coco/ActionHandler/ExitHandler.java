package study.coco.ActionHandler;

import study.coco.Game;

public class ExitHandler extends CommandHandler{

    public ExitHandler(Game game) {
        super(game, "exit");
        this.phrases = new String[]{"exit", "quit", "q", "x"};
        this.message = "Bye, bye! See you soon!";
    }

    @Override
    protected String[] getPhrases() {
        return this.phrases;
    }

}
