import java.util.*;
import java.util.Scanner;
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
    private HashMap <String, Monster> monsterMap = new HashMap<String, Monster>();
    private Monster monster;
    private Player player;
    Scanner myScanner = new Scanner(System.in);
    Scanner enterScanner = new Scanner(System.in);
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private HashMap <String,Item> inventory = new HashMap<String, Item>();
    private Stack<Room> history;
    private Levelbuilder lvls;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        lvls = new Levelbuilder();
        currentRoom = lvls.createRooms();
        parser = new Parser();
        history = new Stack<Room>();
        monsterMap = new HashMap<>();
    }

    /**
     * Creates a new player.
     */
    public void createPlayer(String naam)
    {
        int hp = 10;
        int xp = 0;
        player = new Player(naam, currentRoom, hp, xp);
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
        String naam = naamGetter();
        if(naam == null){
            printWelcome();
            return;
        }
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        createPlayer(naam);
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    /**
     * kijkt of je 2 maal dezelfde naam invoert zodat je niet met een typfout speelt
     */
    private String naamGetter(){
        System.out.println("what is your name?");
        String naam = vraagNaam();
        System.out.println("enter name again please");
        String naam2 = vraagNaam();
        if(naam.equals(naam2)){
            return naam;
        }
        else{
            return null;
        }
    }

    /**
     * vraag de naam van de autistische gebruiker lol
     */
    private String vraagNaam(){
        Scanner reader;
        reader = new Scanner(System.in);
        String inputLine;   // will hold the full input line
        System.out.print("> ");     // print prompt
        inputLine = reader.nextLine();
        return inputLine;
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

        switch(commandWord)
        {
            case "help" : printHelp();
            break;
            case "go" : goRoom(command);
            break;
            case "quit" : wantToQuit = quit(command);
            break;
            case "look" : look();
            break;
            case "inventory" : printInventory(); getTotalWeight();
            break;
            case "get" : getItem(command);
            break;
            case "drop" : dropItem(command);
            break;
            case "drink" : drink();
            break;
            case "back" : goBackRoom();
            break;
            case "attack" : attackMonster(command);
            break;
            case "superAttack" : superAttackMonster(command);
            break;
        }
        return wantToQuit;
    }

    // implementations of user commands:
    public void attackMonster(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what we need to attack...
            System.out.println("Attack what?");
            return;
        }
        String monsterName = command.getSecondWord();
        Monster currentMonster = monsterMap.get(monsterName);
        if(currentMonster == null){

            String monsterAttacked = command.getSecondWord();
            int health = monster.getHP();

            monster.setHP(health - player.getAttack());
        }
    }

    public void superAttackMonster(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what we need to attack...
            System.out.println("SuperAttack what?");
            return;
        }
        String monsterSupperAttacked = command.getSecondWord();
        String monsterName = command.getSecondWord();
        Monster newMonster = monsterMap.get(monsterName);
        int healthPoints = monster.getHP();
        
        if(newMonster == null){
            monster.setHP(healthPoints - player.getSuperAttack());
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
            currentRoom.addItem(newItem);
            System.out.println("Dropped: " + itemName); 
            System.out.println("your total weight: " + getTotalWeight());
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
            if (getTotalWeight() > 50){
                inventory.remove(itemName);
                currentRoom.addItem(newItem);
                System.out.println("this items is too heavy");
            }
            else
            {
                System.out.println("New inventory weight: " + getTotalWeight());
            }
        }
    }

    private int getTotalWeight(){
        int output = 0;
        for(String itemName : inventory.keySet()){
            output = output + inventory.get(itemName).getWeight();
        }
        return output;
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
        if(nextRoom != null){
            player.setRoom(nextRoom);
            System.out.println(player.getRoom().getLongDescription());
        }

        if(!nextRoom.getKeyRoom()){
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
        else{history.push(currentRoom);
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