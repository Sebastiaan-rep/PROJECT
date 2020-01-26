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

    public void setKeyroom(boolean key)
    {
        keyroom = key;
    }

    public boolean getKeyRoom()
    {
        return keyroom;
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    public void addMonster(Monster monster)
    {
        monsterInRoom.put(monster.getName(), monster);
    }

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
     * Retouneer een string met daarin de uitgangen van de ruimte,
     * bijvoorbeeld "Exits: north west".
     * @return Een omschrijving van de aanwezige uitgangen in de 
     * ruimte.
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
     * Retourneert een string die de uitgangen van de ruimte beschrijft,       
     * bijvoorbeeld:      
     * "Exits: north west".      
     */ 
    public Room getExit(String direction)
    {
        return exits.get(direction);

    }

    public Item getItem(String name){
        //return items.get(name);
        return itemsInRoom.get(name);
    }

    public void removeItem(String itemName){
        itemsInRoom.remove(itemName);

    }

    public void setItem(Item newitem){
        itemsInRoom.put(newitem.name, newitem);
    }

    public String getRoomItems(){
        String output = "";
        for(String itemName : itemsInRoom.keySet()){
            output += itemsInRoom.get(itemName).getName() + itemsInRoom.get(itemName).getDescription() + ", Weight: " + itemsInRoom.get(itemName).getWeight() + "\n";
        }
        return output;
    }

    /**
     * Retourneer een lange omschrijving van deze ruimte, van de vorm:
     *      Je bent nu in de Puzzelroom.
     *      Exits: east south
     * @return Een omschrijving van de ruimte en haar uitgangen.
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

}

