
/**
 * class Levelbuilder - geef hier een beschrijving van deze class
 *
 * @author (jouw naam)
 * @version (versie nummer of datum)
 */
public class Levelbuilder
{
    /**
     * Create all the rooms and link their exits together.
     */
    public Room createRooms()
    {
        Room outside, hallway, mainroom, puzzelroom, dinningroom, basement, 
        greenhouse, throneroom;
        Monster rat, boss, knight;
        Item health_potion, key, sword;
        
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

        //create monsters
        rat = new Monster("Rat Stijn", "a monster little hyperactief mouse,", 100, 10, true);
        boss = new Monster("King Teun", "Hmm... this is the end boss ?! whats wrong with the developers", 200, 30, true);
        knight = new Monster("Knight Sebastiaan","LOL he looks like the knight from shrek ;)", 120, 20, true);

        // All items in the rooms
        key = new Item("key", 5,"this is a weird key, wonder where it goes");
        health_potion = new Item("health_potion", 3,": Refreshing drink, not as refreshing as pils");
        hallway.addMonster(rat);

        return outside;  // start game outside
    }

}
