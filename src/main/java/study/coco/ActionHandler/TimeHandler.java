package study.coco.ActionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import study.coco.Game;


public class TimeHandler extends CommandHandler {

    private static final String[] phrases = {"time", "t", "date", "d"};
    private static final String message = "Es ist 12:12 Uhr am 12.12.1212.";
    private static final String type = "time";

    public TimeHandler(Game game) {
        super(game, type, phrases, message);
    }

    
}
