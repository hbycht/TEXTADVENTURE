package study.coco.ActionHandler;

import study.coco.Game;

/**
 * This Handler reacts to ____ commands.
 */
public class Template_CommandHandler extends CommandHandler {

    private static final String[] commands = {"abc", "xyz"};
    private static final String message = "Template CommandHandler Message!";
    private static final String type = "default";

    public Template_CommandHandler(Game game) {
        super(game, type, commands, message);
    }

    
}
