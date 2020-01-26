
import java.util.*;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Player player;
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Room trap_room;
    private HashMap <String,Item> inventory = new HashMap<String, Item>();
    private Item weight;
    private Stack<Room> history;
    private Menu menu;
    private static int limitOfMoves;
    private static int numberOfMoves;

    Room outside, hallway, mainroom, puzzleroom, diningroom, basement, 
    greenhouse, bossroom, backyard, kitchen;

    public static void main(final String[] args){
        new Menu().runMenu();
    }

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        //Player = new Player();
        createRooms();
        parser          = new Parser();
        history         = new Stack<Room>();
        menu            = new Menu();
    }

    public void menu(){
        menu.runMenu();
    }

    /**
     * Create all the rooms and link their exits together.
     * Create all items and place them inside of the rooms.
     * @author Jia Wei Wang and Sebastiaan Rep
     */
    private void createRooms()
    {

        // create the rooms
        outside     = new Room("outside the main entrance of king Java's Castle");
        hallway     = new Room("in the hallway");
        mainroom    = new Room("A big main room with expensive furniture");
        puzzleroom  = new Room("in puzzle room has alot of toys and games");
        diningroom  = new Room(" in Dinning room with a big table and lots of plastic food");
        basement    = new Room("in a big basement for random stuff en pils");
        greenhouse  = new Room("This warm greenhouse has alot of fuit and vegetables");
        backyard    = new Room("This is just a simple backyard with neglected plants and a few candles");
        kitchen     = new Room("This room is very dark, smells like the kitchen. I need some light");
        bossroom    = new Room("OH S** this is the boss room");
        trap_room   = new Room("Empty Room with a opening to the east.");

        // initialise room exits
        outside.setExits        ("east", hallway);

        hallway.setExits        ("east", mainroom);

        mainroom.setExits       ("north", puzzleroom);
        mainroom.setExits       ("south", diningroom);

        puzzleroom.setExits     ("east", greenhouse);
        puzzleroom.setExits     ("south", mainroom);

        greenhouse.setExits     ("east", bossroom);
        greenhouse.setExits     ("south", backyard);
        greenhouse.setExits     ("west", puzzleroom);

        backyard.setExits       ("north", greenhouse);
        backyard.setExits       ("south", kitchen);
        backyard.setExits       ("west", mainroom);

        kitchen.setExits        ("north", backyard);
        kitchen.setExits        ("west", diningroom);
        kitchen.setExits        ("east", trap_room);

        diningroom.setExits     ("down", basement);
        diningroom.setExits     ("east", diningroom);

        basement.setExits       ("up", diningroom);

        trap_room.setExits      ("west", kitchen);

        // All items in the rooms
        hallway.setItem         (new Item("key", 5, " this is a weird key, wonder where it goes"));

        mainroom.setItem        (new Item("sword", 2));

        diningroom.setItem      (new Item("health_potion", 3,": Refreshing drink, not as refreshing as pils"));

        basement.setItem        (new Item("health_potion", 5, ": Refreshing drink, not as refreshing as pils"));

        //Monster in the rooms

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Legend of Jia Wei!");
        System.out.println("This is where one boy it's journey begins!.");
        System.out.println("Type 'help' if you need help.");

        chooseLevel();

        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    private void chooseLevel()
    {
        // Choosing a level (asking to the user through the terminal)
        Scanner reader = new Scanner(System.in);
        System.out.println("Please choose a level : Easy 20 moves(0) - Medium 16 moves(1) - Hard 14 moves (2)");
        // Find the chosen level and alter the number of moves accorfing to the chosen one
        try {
            switch (reader.nextInt()) {
                case 0:
                limitOfMoves = 20;
                System.out.println("You've chosen the easy way to win ! - Number of moves : " + limitOfMoves);
                break;
                case 1:
                limitOfMoves = 16;
                System.out.println("You've chosen the medium level :)- Number of moves : " + limitOfMoves);
                break;
                case 2:
                limitOfMoves = 14;
                System.out.println("It's gonna be hard this way :@  - Number of moves : " + limitOfMoves);
                break;
                default:
                limitOfMoves = 20;
                System.out.println("Unkown command - Default level : Easy - Number of moves : " + limitOfMoves);
                break;
            }
        } catch(Exception e){
            limitOfMoves = 20;
            System.out.println("Unkown command - Default level : Easy - Number of moves : " + limitOfMoves);
        }
    }

    public static boolean countMove(){
        // Count a move
        numberOfMoves++;

        // Give some informations concerning the number of moves
        if (numberOfMoves < limitOfMoves) {
            System.out.println("Current number of moves : " + numberOfMoves);
            System.out.println("Moves left : " + (limitOfMoves - numberOfMoves));
            return false;
            // Ending the game if the number of moves is reached
        } else {
            System.out.println("You have reached the maximum number of moves");
            System.out.println("By the way, GAME OVER ! ");
            System.out.println();
            System.out.println();
            return true;
        }
    }

    /**
     * Given a command, process (t+hat is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")){
            printHelp();
        }
        else if (commandWord.equals("go")){
            goRoom(command);
        }
        else if (commandWord.equals("look" )){
            look();
        }
        else if (commandWord.equals("quit")){
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("drink")){
            drink();
        }else if (commandWord.equals("inventory")){
            printInventory();
        }else if (commandWord.equals("back")){
            goBack(command);
        }else if (commandWord.equals("get")) {
            getItem(command);
        }else if (commandWord.equals("drop")) {
            dropItem(command);
        }else if (commandWord.equals("back")){
            goBackRoom();
        }else if (commandWord.equals("fight")){
            fight();
        }
        return wantToQuit;
    }

    // implementations of user commands:
    /*/**
     * Attack a monster that is in the room
     * 
     * @param command
     */
    /*
    private void attack(Command command) {
    if (!command.hasSecondWord()) {
    // if there is no second word, we don't who to attack
    setChanged();
    notifyObservers("Attack what?");
    return;
    }

    Room currentRoom = player1.getCurrentPlayerRoom();
    Monster monster = currentRoom.getMonster(command.getSecondWord());

    if (monster == null) {
    // There is no monster by that name in the room
    setChanged();
    notifyObservers("There is no monster called "
    + command.getSecondWord() + "!");
    }
    return;
    }
     */

    /**
     * Engage battle with creature
     */

    public void fight(){
        boolean currentRoommonster = false;
        if ( currentRoommonster!= false) {
            System.out.println("Put your glasses on STUPID!");
        }
        else {
            System.out.println("You attack the monster with your bare hands!" + " \n" + "Only one of you will remain victorious"); 
        }
    }

    private void dropItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Wait a minute drop what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item newItem = inventory.get(itemName);
        if(newItem == null){
            System.out.println("You dont have that item");
        }
        else{
            inventory.remove(itemName);
            currentRoom.setItem(newItem);
            System.out.println("Dropped: " + itemName); 
        }
    }

    private void getItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pickup..
            System.out.println("Get what?");
            return;
        }

        String itemName = command.getSecondWord();
        Item newItem = currentRoom.getItem(itemName);

        if(newItem == null){
            System.out.println("There is no item in this room");
        }
        else{
            inventory.put(itemName, newItem);
            currentRoom.removeItem(itemName);
            System.out.println("Picked up: " + itemName); 
        }
    }

    private void printInventory(){
        String output = "";
        for(String itemName : inventory.keySet()){
            output += inventory.get(itemName).getName() + inventory.get(itemName).getDescription() + " Weight: " + inventory.get(itemName).getWeight() + "\n";
        }
        System.out.println("you are carrying:");
        System.out.println(output);
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommandList());

    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            //return false;
        }

        String direction = command.getSecondWord();

        //try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if(nextRoom == null){
            System.out.println("There is no door!");
        }
        else{
            history.push(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            currentRoom.fightWithMonster();
        }
    }

    public int getNumberOfMoves() 
    {
        return numberOfMoves;
    }

    /**
     * @return the limitOfMoves
     */
    public int getLimitOfMoves() 
    {
        return limitOfMoves;
    }
    
    public void setLimitOfMoves(int lom) 
    {
        limitOfMoves = lom;
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    private void printLocationInfo()
    {
        System.out.println("You are " +                            
            currentRoom.getShortDescription());         
        System.out.print(currentRoom.getExitString());         
        System.out.println(); 
    }

    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }

    private void drink()
    {
        System.out.println("you drank a healing potion and gained 1 lifepoint");
    }

    /**
     * Voert de ruimte in en drukt de beschrijving van deze ruimte af op het scherm.
     */

    private void enterRoom(Room nextRoom)
    {
        previousRoom = currentRoom;
        currentRoom = nextRoom;
        System.out.println(currentRoom.getLongDescription());
    }

    private void goBack(Command command)
    {
        if(command.hasSecondWord())
        {
            System.out.println("Go back to where?");
            return;
        }

        else if(previousRoom == null)
        {
            System.out.println("There is nowhere to go back to!");
            return;
        }

        else
        {
            enterRoom(previousRoom);
        }
    }

    private void goBackRoom(){
        if(history.isEmpty()){
            System.out.println("There is nowhere to go!");
        }
        else{
            currentRoom = history.pop();
            printLocationInfo();
        }

    }
}