package study.coco.ActionHandler;

import study.coco.Game;


public abstract class Handler {
    protected Game game;

    public Handler(Game game) {
        this.game = game;
    }
    
    public abstract boolean matches();
    
    public abstract String handle();
    
}
