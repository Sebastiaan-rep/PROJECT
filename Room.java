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
    private Room northExit;
    private Room southExit;
    private Room eastExit;
    private Room westExit;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room south, Room west) 
    {
        if(north != null) {
            northExit = north;
        }
        if(east != null) {
            eastExit = east;
        }
        if(south != null) {
            southExit = south;
        }
        if(west != null) {
            westExit = west;
        }
    }

    /**      
     * Retourneert een string die de uitgangen van de ruimte beschrijft,       
     * bijvoorbeeld:      
     * "Exits: north west".      
     */ 
    public Room getExit(String direction)
    {
        if(direction.equals("north ")){
            return northExit;
        }
        if(direction.equals("east ")){
            return eastExit;
        }
        if(direction.equals("south ")){
            return southExit;
        }
        if(direction.equals("west ")){
            return westExit;
        }
        return null;

    }

    public String getExitString() {         
        String returnString = "Exits: ";         
        if(northExit != null)             
            returnString += "north ";         
        if(eastExit != null)             
            returnString += "east ";         
        if(southExit != null)            
            returnString += "south ";         
        if(westExit != null)             
            returnString += "west ";         
        return returnString;     } 

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

}
