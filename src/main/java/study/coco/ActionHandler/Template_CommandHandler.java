package study.coco.ActionHandler;

import study.coco.Game;

/**
 * A template to pre-build CommandHandler
 */
public class Template_CommandHandler extends CommandHandler {

    private static final String[] phrases = {"abc", "xyz"};
    private static final String message = "Template CommandHandler Message!";
    private static final String type = "default";

    public Template_CommandHandler(Game game) {
        super(game, type, phrases, message);
    }

    
}
