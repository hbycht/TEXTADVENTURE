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
        this.handlers.add(new UseHandler(this));
        this.handlers.add(new InventoryHandler(this));

        // init all items
        Item water = new Item("water", "A cold & liquid something you can drink.", "o");
        Item fire = new Item("fire", "A burning hot & bright something you can cook with.", "o");
        Item air = new Item("air", "A fresh & volatile something you can blow things with.", "o");
        Item earth = new Item("earth", "A thick & nutritious something you can eat.", "o");
        Item stone = new Item("mysterious stone", "A mysterious shinny stone. I wonder what I can do with it...", "o");

        // init all locations
        Location start = new Location("center", "in the", "The place where everything began.");
        Location north = new Location("north", "in the", "It's cold and freezy.");
        Location south = new Location("south", "in the", "Oh nice, it's super sunny.");
        Location west = new Location("west", "in the", "Oh wow, so many corn fields.");
        Location east = new Location("east", "in the", "A quite and calm place.");

        // init all gates
        Gate gC_N = new Gate("gate to north", "A sky gate with a small sign on it that says \"North\".", earth);
        Gate gC_S = new Gate("gate to south", "A sky gate with a small sign on it that says \"South\".", water);
        Gate gC_W = new Gate("gate to west", "A sky gate with a small sign on it that says \"West\".");
        Gate gC_E = new Gate("gate to east", "A sky gate with a small sign on it that says \"East\".");

        // set up all connections between locations
        // every gate should appear twice, so the assignment automation for the gate endings will work correctly.
        start.setGates(gC_N, gC_E, gC_S, gC_W);
        north.setGates(null, null, gC_N, null);
        east.setGates(null, null, null, gC_E);
        south.setGates(gC_S, null, null, null);
        west.setGates(null, gC_W, null, null);


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
        this.commandParts = this.lineInput.split(" ", 2);

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

        // debug
        System.out.println("actual Gate: " + (this.player().getActualGate() != null ? this.player().getActualGate().getName() : "no gate selected"));

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
     * Get only the command part of the user input as combined {@code String}.
     * @return {@code String} that holds the actual command from the user input.
     */
    public String getInputObject(){
        if(this.commandParts.length > 1)
            return this.commandParts[1];
        else
            return "";
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