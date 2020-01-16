
import java.util.Stack;
import java.util.ArrayList;
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
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> backStack;
    //private Player player;
    ArrayList<Item> inventory = new ArrayList<Item>();

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        backStack = new Stack();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, kelder;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        kelder = new Room("in de kelder");

        // initialise room exits
        outside.setExits("east", theater);
        outside.setExits("south", lab);
        outside.setExits("west", pub);

        theater.setExits("west", outside);

        pub.setExits("east", outside);

        lab.setExits("north", outside);
        lab.setExits("east", office);

        office.setExits("west", lab);
        office.setExits("down", kelder);
        
        kelder.setExits("up", office);
        
        lab.setItem(new Item("Banaan"));
        
       

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
     * Given a command, process (that is: execute) the command.
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
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look" )){
            look();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("drink")) {
            drink();
        }else if (commandWord.equals("inventory")) {
            printInventory();
        }
        
        
        /*else if (commandWord.equals("drop"))
            dropItem(command);
        else if (commandWord.equals("take"))
            takeItem(command);*/
        return wantToQuit;
    }

    // implementations of user commands:
    private void printInventory(){
        String output = "";
        for(int i =0; i< inventory.size(); i++){
            output += inventory.get(i).getDescription() + " " ;
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
        //parser.showCommands();
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
            return;
        }

        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);
        
        if(nextRoom == null){
            System.out.println("There is no door!");
        }
        else{
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
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
    /*
    private void dropItem(Command command)
    {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }
        
        String droppedItem = command.getSecondWord();
        
        // Drop it
        
        Item temp = player.dropInventory(droppedItem);
        if (temp != null)
        {
            
            // Add it to the room's items
            player.getCurrentRoom().setItem(temp.getName(), temp.getItemDescription());
            
            // Refresh inventory
            System.out.println(player.getCurrentRoom().getLongDescription());
            System.out.println(player.getInventoryString());
        }      
        
    }
    
    /** 
     * "Take" was entered. Takes the specified item if it's in 
     * the room, otherwise throws an error.
    */
    /*
    private void takeItem(Command command)
    {
       if (!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Take what?");
            return;
        }
        
        String desiredItem = command.getSecondWord();
              
        // Remove it from the room's items
        Item temp = player.getCurrentRoom() .delItem(desiredItem);
        if (temp != null)
        {     
            // Add it to player's inventory
            player.addInventory(temp.getName(), temp.getItemDescription());
            
            // Refresh inventory
            System.out.println(player.getCurrentRoom().getLongDescription());
            System.out.println(player.getInventoryString());
        }
    }
    */
}
