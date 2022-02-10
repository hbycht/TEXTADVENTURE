package study.coco.ActionHandler;

import study.coco.Game;


public abstract class Handler {
    protected Game game;
    protected String type;
    protected String message;

    public Handler(Game game, String type) {
        this.game = game;
        this.type = type;
    }

    public abstract boolean matches();

    public abstract void handle();

    public String getType() {
        return this.type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
