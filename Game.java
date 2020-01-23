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
    private HashMap <String,Item> inventory = new HashMap<String, Item>();
    private Item weight;
    Room outside, hallway, mainroom, puzzelroom, dinningroom, basement, 
    greenhouse, throneroom;
    //private Room previousRoom;
    private Stack<Room> history;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        //Player = new Player();
        createRooms();
        parser = new Parser();
        history = new Stack<Room>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {

        // create the rooms
        outside = new Room("outside the main entrance of the Castle");
        hallway = new Room("in the hallway");
        mainroom = new Room("A big main room with expensive furniture");
        dinningroom = new Room(" in Dinning room with a big table");
        basement = new Room("in a big basement for random stuff");
        greenhouse = new Room("This warm greenhouse has alot of fuit and vegetables");
        puzzelroom = new Room("in puzzel room has alot of toys and games");
        throneroom = new Room("OH S** this is the boss room");
        // initialise room exits
        outside.setExits("east", hallway);

        hallway.setExits("east", mainroom);

        mainroom.setExits("north", puzzelroom);
        mainroom.setExits("south", dinningroom);

        dinningroom.setExits("down", basement);
        dinningroom.setExits("up", dinningroom);
        dinningroom.setExits("east", throneroom);

        // All items in the rooms
        hallway.setItem(new Item("key", 5, " this is a weird key, wonder where it goes"));

        outside.setItem(new Item("sword", 2));
        mainroom.setItem(new Item("health_potion", 3,": Refreshing drink, not as refreshing as pils"));

        basement.setItem(new Item("health_potion", 5, ": Refreshing drink, not as refreshing as pils"));

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
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
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
    /**
     * Attack a monster that is in the room
     * 
     * @param command
     */
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