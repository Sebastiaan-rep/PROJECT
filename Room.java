import java.util.*;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.        
    private HashMap <String, Monster> monsterInRoom;    
    private boolean keyroom;
    private HashMap<String, Item> itemsInRoom = new HashMap<String, Item>();
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        monsterInRoom = new HashMap<String, Monster>();
        itemsInRoom = new HashMap<String, Item>();
        keyroom = false;
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }
    /**
     * adds a monster in a room
     * 
     */
    public void addMonster(Monster monster)
    {
        monsterInRoom.put(monster.getName(), monster);
    }
    /**
     * loops throught the monsters and print the monster that is currently in the room
     * 
     */
    private String showMonsters(){
        String returnMonsters = "";
        if(monsterInRoom != null){
            for(String monsterName: monsterInRoom.keySet()){
                returnMonsters += "There is a " + monsterInRoom.get(monsterName).getName() + " in the room!\n";
            }
        }
        return returnMonsters;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param direction De richting van de uitgang
     * @param neighbour De ruimte in die richting
     */
    public void setExits(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * Return a string with the exits if the room
     * Example: Exits: north west
     * @return A description of all the the exits of the room
     */
    public String getExitString()
    {
        String returnString = "\nExits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys){
            returnString += " " + exit;
        }
        returnString += "\nItems in the room: \n";
        returnString += getRoomItems() + "\n";
        returnString += showMonsters() + "\n";
        return returnString;
    }

    /**      
     * Get the exits of the room.   
     */ 
    public Room getExit(String direction)
    {
        return exits.get(direction);

    }
    /**
     * gets item name from the room
     */
    public Item getItem(String name){
        //return items.get(name);
        return itemsInRoom.get(name);
    }
    /**
     * removes items from the room
     */
    public void removeItem(String itemName){
        itemsInRoom.remove(itemName);

    }
    /**
     * set new items in the room
     */
    public void setItem(Item newitem){
        itemsInRoom.put(newitem.name, newitem);
    }
    
    
    /**
     * prints all the items in the room
     * @param name, description, weight
     * @return een overzicht van items in de room
     */
    public String getRoomItems(){
        String output = "";
        for(String itemName : itemsInRoom.keySet()){
            output += itemsInRoom.get(itemName).getName() + itemsInRoom.get(itemName).getDescription() + ", Weight: " + itemsInRoom.get(itemName).getWeight() + "\n";
        }
        return output;
    }

    /**
     * Returns a string of the current room description and exits
     *      You are in the puzzle Room
     *      Exits: east south
     * @return description of the room with the exits
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

}

