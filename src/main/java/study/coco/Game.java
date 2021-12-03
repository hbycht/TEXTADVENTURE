package study.coco;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import study.coco.ActionHandler.*;

import java.io.IOException;
import java.util.ArrayList;

public class Game {
    private Terminal terminal;
    private LineReader reader;

    private String lineInput;

    private ArrayList<Handler> handlers = new ArrayList<>();

    private boolean finished;
    private int xPos;

    public Game() throws IOException {
        terminal = TerminalBuilder.builder().jansi(true).system(true).build();
        reader = LineReaderBuilder.builder().terminal(terminal).history(new DefaultHistory()).build();

        lineInput = "";

        this.handlers.add(new TimeHandler(this));
        this.handlers.add(new ExitHandler(this));
//        this.handlers.add(new CommandHandler(this, new String[]{"go", "g"}, "You went one step!"));

        finished = false;
        xPos = 0;
    }

    public void run() {


        while (!this.finished) {
            this.printUpdate();
        }
    }

    public void exit() {
        this.finished = true;
    }

    private String printUpdate() {
        this.lineInput = this.reader.readLine("What do you want to do?\n >> ");
        Handler currentHandler = new ErrorHandler(this);

        for (Handler handler : this.handlers) {
            if (handler.matches()) {
                currentHandler = handler;
                break;
            }
        }

        String update = currentHandler.handle();

        System.out.println(update + "\n");

        return update;
    }

    public String getLineInput() {
        return lineInput;
    }
}
