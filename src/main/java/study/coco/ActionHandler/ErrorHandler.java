package study.coco.ActionHandler;

import study.coco.Game;


public class ErrorHandler extends Handler {

    public ErrorHandler(Game game) {
        super(game);
    }

    public String handle() {
        return "What do you mean with [" + this.game.getLineInput() + "]?";
    }

    @Override
    public boolean matches() {
        return true;
    }
}
