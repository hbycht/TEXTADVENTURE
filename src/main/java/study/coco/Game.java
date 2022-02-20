package study.coco;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import study.coco.ActionHandler.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class Game {
    private Terminal terminal;
    private LineReader reader;

    // little history of past handlers for debugging
    private int handleCount;
    public String handleTypes = "";

    // storing the current terminal input
    private String lineInput;
    private String[] commandParts;

    // some message presets
    private String inputCall = " >> ";
    private String inputMsg = "\nWhat should I do next?";

    // list of available input handlers
    private ArrayList<Handler> handlers = new ArrayList<>();

    // init an instance of the error handler
    Handler errorHandler = new ErrorHandler(this);

    // there is always a current handler for handling the terminal input
    Handler currentHandler;

    // init player
    private Player player;

    // game state
    private boolean finished;

    public Game() throws IOException {
        // init terminal & input instances
        terminal = TerminalBuilder.builder().jansi(true).system(true).build();
        reader = LineReaderBuilder.builder().terminal(terminal).history(new DefaultHistory()).build();

        lineInput = "";

        // init & add all necessary game handlers to the handler list
        this.handlers.add(new ExitHandler(this));
        this.handlers.add(new MoveHandler(this));
        this.handlers.add(new TakeHandler(this));
        this.handlers.add(new DropHandler(this));

        // init all items
        Item water = new Item("water", "water", "A cold & liquid something you can drink.", "o");
        Item fire = new Item("fire", "fire", "A burning hot & bright something you can cook with.", "o");
        Item air = new Item("air", "air", "A fresh & volatile something you can blow things with.", "o");
        Item earth = new Item("earth", "earth", "A thick & nutritious something you can eat.", "o");
        Item stone = new Item("stone", "mysterious stone", "A mysterious shinny stone. I wonder what I can do with it...", "o");

        // init all locations
        Location start = new Location("start", "center", "in the", "The place where everything began.");
        Location north = new Location("n", "north", "in the", "It's cold and freezy.");
        Location south = new Location("s", "south", "in the", "Oh nice, it's super sunny.");
        Location west = new Location("w", "west", "in the", "Oh wow, so many corn fields.");
        Location east = new Location("e", "east", "in the", "A quite and calm place.");

        // set up all connections between locations
        start.setGates(north, east, south, west);
        north.setGates(null, null, start, null);
        east.setGates(null, null, null, start);
        south.setGates(start, null, null, null);
        west.setGates(null, start, null, null);


        // add items to locations
        north.inventory().addItem(air);
        north.inventory().addItem(air);
        north.inventory().addItem(air);
        south.inventory().addItem(fire);
        south.inventory().addItem(fire);
        west.inventory().addItem(earth);
        east.inventory().addItem(water);

        // init the player
        this.player = new Player(start);
        // add some items to players inventory
        player.inventory().addItem(stone);

        finished = false;
    }

    /**
     *  Basic game logic.
     */
    public void run() {
        System.out.println("TEXT ADVENTURE " + this.getClass().getSimpleName().toUpperCase(Locale.ROOT) + " by Henning Brode © 2022\n");

        // first terminal outputs as overview
        System.out.println(this.player.getPosition().getFullDescription());
        System.out.println(inputMsg);

        // run until game finished
        while (!this.finished) {
            // get current handler and process game logic
            currentHandler = this.getCurrentHandler(); // must be the first operation before handle() & printUpdate()
            this.processGameLogic();
        }
    }

    /**
     * Returns the current handler if it matches the user input.
     * @return The current {@code Handler} if the given user input matches any handler command, else an {@code ErrorHandler}.
     */
    private Handler getCurrentHandler() {
        // ask for and store the given input
        this.lineInput = this.reader.readLine(inputCall).trim();

        // parse command into parts
        this.commandParts = this.lineInput.split(" ");

        // check if user input matches a valid command for a handler
        for (Handler handler : this.handlers) {
            if (handler.matches(this.getInputCommand())) {
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
        // for debugging
//        this.handleCount++;
//        this.handleTypes += currentHandler.getType() + " ";

        // execute the handler actions
        currentHandler.handle();

        // update the terminal
        this.printHandlerMessage();

        // ask for next input
        if(currentHandler.getType() != "error" && currentHandler.getType() != "exit") // but don't ask for an ERROR or EXIT command
            System.out.println(inputMsg);
    }

    /**
     * Method to print the reaction to the user.
     */
    private void printHandlerMessage(){
        // just for debugging
//        System.out.println("[" + this.handleCount + "] " + this.handleTypes);

        // print Handler message
        System.out.println("\n" + currentHandler.getMessage());
    }

    /**
     * Get the full user input.
     * @return Full user input from terminal.
     */
    public String getLineInput(){
        return this.lineInput;
    }

    /**
     * Get the full user answer to the given terminal message.
     * @param terminalMsg Message the user should see.
     * @return Full {@code String} that holds the answer to the given message.
     */
    public String getLineInput(String terminalMsg){
        this.lineInput = this.reader.readLine(terminalMsg).trim();
        return this.lineInput;
    }

    /**
     * Get only the command part of the user input.
     * @return {@code String} that holds the actual command from the user input.
     */
    public String getInputCommand(){
        return this.commandParts[0];
    }

    /**
     * Get only the command part of the user input.
     * @return {@code String} that holds the actual command from the user input.
     */
    public String getInputObject(){
        return this.commandParts[1];
    }

    /**
     * Finish the game.
     */
    public void finish(){
        this.finished = true;
    }

    /**
     * Returns the player of the Game instance.
     * @return {@code Player} of the game.
     */
    public Player player(){
        return this.player;
    }
}