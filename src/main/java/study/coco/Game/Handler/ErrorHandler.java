package study.coco.Game.Handler;

import study.coco.Game.Game;

/**
 * This Handler reacts if the player types an unknown command.
 */
public class ErrorHandler extends Handler {

    public ErrorHandler(Game game) {
        super(game, "error");
    }

    @Override
    public String getMessage() {
        return "Was meinst du mit '" + this.game.getLineInput() + "'?";
    }

    @Override
    public boolean matches(String command) {
        return true;
    }

    @Override
    public void handle() {

    }
}
