package study.coco.ActionHandler;

import study.coco.Game;


public class ErrorHandler extends Handler {

    public ErrorHandler(Game game) {
        super(game, "error");
    }

    @Override
    public String getMessage() {
        return "What do you mean with '" + this.game.getLineInput() + "'?";
    }

    @Override
    public boolean matches() {
        return true;
    }

    @Override
    public void handle() {

    }
}