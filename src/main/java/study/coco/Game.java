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

    // init player
    private Player player;

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
        this.handlers.add(new MoveHandler(this));
        this.handlers.add(new CommandHandler(this, "move", new String[]{"move", "m", "go", "g"}, "You went 1 step."));

        // init all items
        Item water = new Item("water", "water", "A cold & liquid something you can drink.", "o");
        Item fire = new Item("fire", "fire", "A burning hot & bright something you can cook with.", "o");
        Item air = new Item("air", "air", "A fresh & volatile something you can blow things with.", "o");
        Item earth = new Item("earth", "earth", "A thick & nutritious something you can eat.", "o");
        Item stone = new Item("stone", "mysterious stone", "A mysterious shinny stone. I wonder what I can do with it...", "o");

        // init all locations
        Location start = new Location("start", "center", "The place where everything began.");
        Location north = new Location("n", "north", "It's cold and freezy.");
        Location south = new Location("s", "south", "Oh nice, it's super sunny.");
        Location west = new Location("w", "west", "Oh wow, so many corn fields.");
        Location east = new Location("e", "east", "A quite and calm place.");

        // set up all connections between locations
        start.setGates(north, east, south, west);
        north.setGates(null, null, start, null);
        east.setGates(null, null, null, start);
        south.setGates(start, null, null, null);
        west.setGates(null, start, null, null);


        // add items to locations
        north.inventory().addItem(air);
        south.inventory().addItem(fire);
        west.inventory().addItem(earth);
        east.inventory().addItem(water);

        // init the player
        this.player = new Player(start);
        // add some items to players inventory
        player.inventory().addItem(stone);

        finished = false;
        xPos = 0;
    }

    /**
     *  Basic game logic.
     */
    public void run() {
        System.out.println("TEXT ADVENTURE " + this.getClass().getSimpleName().toUpperCase(Locale.ROOT) + " by Henning Brode © 2022\n");

        printUpdate(false);

        // run until game finished
        while (!this.finished) {
            // get current handler and process game logic
            currentHandler = this.getCurrentHandler(); // must be the first operation before handle() & printUpdate()
            this.processGameLogic();
        }
    }

    /**
     * Finish the game.
     */
    public void finish(){
        this.finished = true;
    }

    /**
     * Method that confirms the exit request.
     */
    public void exit() {
        // show exit confirmation message
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
        this.lineInput = this.reader.readLine("What do you want to do?\n >> ").trim();

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
        // for debugging
//        this.handleCount++;
//        this.handleTypes += currentHandler.getType() + " ";

        // execute the handler actions
        currentHandler.handle();
        // update the terminal
        this.printUpdate();
    }

    /**
     * Method to print the reaction to the user.
     */
    private void printUpdate() {
        // just for debugging
//        System.out.println("[" + this.handleCount + "] " + this.handleTypes);

        // print Handler message
        System.out.println(currentHandler.getMessage());
        // print location description
        System.out.println(this.player.getPosition().getDescription());
        // print items the player can see
        System.out.println("I can see some items:");
        this.player.getPosition().inventory().getItems().forEach(item -> System.out.print(item.getName() + ", "));
        System.out.println("");
    }

    private void printUpdate(boolean withHandler) {
        // just for debugging
//        System.out.println("[" + this.handleCount + "] " + this.handleTypes);

        if (withHandler)
            // print Handler message
            System.out.println(currentHandler.getMessage());

        // print location description
        System.out.println(this.player.getPosition().getDescription());
        // print items the player can see
        System.out.println("I can see some items:");
        this.player.getPosition().inventory().getItems().forEach(item -> System.out.print(item.getName() + ", "));
        System.out.println("");
    }

    /**
     * Get the user input.
     * @return User input from terminal.
     */
    public String getLineInput() {
        return this.lineInput;
    }

    /**
     * Get the user input.
     * @param terminalMsg Message the user should see.
     * @return {@code String} that holds the answer to the given message.
     */
    public String getLineInput(String terminalMsg){
        this.lineInput = this.reader.readLine(terminalMsg).trim();
        return this.lineInput;
    }

    public Player player() {
        return player;
    }
}