package study.coco;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import study.coco.ActionHandler.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class Game {
    private Terminal terminal;
    private LineReader reader;

    // little history of past handlers for debugging
    private int handleCount;
    public String handleTypes = "";

    // storing the current terminal input
    private String lineInput;

    // available input handlers in a list
    private ArrayList<Handler> handlers = new ArrayList<>();
    // init an instance of the error handler
    Handler errorHandler = new ErrorHandler(this);
    // there is always a current handler for handling the terminal input
    Handler currentHandler;

    // game state and player position
    private boolean finished;
    private int xPos;

    public Game() throws IOException {
        // init terminal & input instances
        terminal = TerminalBuilder.builder().jansi(true).system(true).build();
        reader = LineReaderBuilder.builder().terminal(terminal).history(new DefaultHistory()).build();

        lineInput = "";

        // init & add all necessary game handlers to the handler list
        this.handlers.add(new TimeHandler(this));
        this.handlers.add(new ExitHandler(this));
        this.handlers.add(new CommandHandler(this, "move", new String[]{"move", "m", "go", "g"}, "You went 1 step."));

        // init all items
        // ...

        // init all locations
        Location start = new Location("start", "center", "The place where everything began.");
        Location north = new Location("n", "north", "It's cold and freezy.");
        Location south = new Location("s", "south", "Oh nice, it's super sunny.");
        Location west = new Location("w", "west", "Puuh, it's a very windy place.");
        Location east = new Location("e", "east", "A quite and calm place.");

        // init the player
        Player player = new Player(start);
        // add some items to players inventory
        // ...

        finished = false;
        xPos = 0;
    }

    /**
     *  Basic game logic.
     */
    public void run() {
        // run until game finished
        while (!this.finished) {
            // get current handler and process game logic
            currentHandler = this.getCurrentHandler(); // must be the first operation before handle() & printUpdate()
            this.processGameLogic();
        }
    }

    /**
     * Method that confirms the exit request.
     */
    public void exit() {
        // show confirmation message
        this.lineInput = this.reader.readLine("Do you really want to quit?\n(Type \"Y\" to confirm.)\n >> ");
        // check if the user inputs "y" or "yes".
        String[] confirms = new String[]{"y","yes"};
        if(Arrays.asList(confirms).contains(this.lineInput)) {
            // end game
            this.finished = true;
            this.printUpdate();
        }
    }

    /**
     * Returns the current handler if it matches the user input.
     * @return The current {@code Handler} if the given user input matches any handler command, else an {@code ErrorHandler}.
     */
    private Handler getCurrentHandler() {
        // ask for and store the given input
        this.lineInput = this.reader.readLine("What do you want to do?\n >> ");

        // check if user input matches a valid command for a handler
        for (Handler handler : this.handlers) {
            if (handler.matches()) {
                // if it's a match return this handler
                return handler;
            }
        }
        // else return default error handler
        return errorHandler;
    }

    /**
     * Processes the game logic: it executes the handle function of the current handler.
     */
    private void processGameLogic() {
        String type = currentHandler.getType();

        this.handleCount++;
        this.handleTypes += type + " ";


        if(Objects.equals(type, "exit")) {
            // special case for exit requests
            this.exit();
        } else {
            // else react to user
            this.printUpdate();
        }
    }

    /**
     * Method to print the reaction to the user.
     */
    private void printUpdate() {
        System.out.println("[" + this.handleCount + "] " + this.handleTypes);
        String update = currentHandler.getMessage();
        System.out.println(update + "\n");
    }

    /**
     * Get the user input.
     * @return User input from terminal.
     */
    public String getLineInput() {
        return lineInput;
    }
}