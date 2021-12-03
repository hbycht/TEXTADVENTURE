package study.coco.ActionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import study.coco.Game;


public class TimeHandler extends CommandHandler {

    public TimeHandler(Game game) {
        super(game);
        this.phrases = new String[]{"time", "t", "date", "d"};
        this.message = "Es ist 12:12 Uhr am 12.12.1212.";
    }

    
}
