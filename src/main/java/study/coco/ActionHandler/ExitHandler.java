package study.coco.ActionHandler;

import study.coco.Game;

public class ExitHandler extends CommandHandler{

    private static final String[] phrases = {"exit", "quit", "q", "x"};
    private static final String message = "Bye, bye! See you soon!";
    private static final String type = "exit";

    public ExitHandler(Game game) {
        super(game, type, phrases, message);
    }

}
