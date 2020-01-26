
/**
 * class Monster - 
 *  This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class beholds the makings of a monster in the world of zuul
 *
 * @author Jia wei Wang, Sebastiaan Rep
 * @version 26-01-2020
 */
public class Monster
{
    private String name;
    private String description;
    private Room currentRoom;
    private int HP;
    private int power;


    /**
     * Constructor voor objects van class Monster
     */
    public Monster(String name, String description, int HP, int power)
    {
        this.name = name;
        this.description = description;
        this.HP = HP;
        this.power = power;
    }
    /**
     * @return monster name
     */
    public String getName()
    {
        return name;
    }
    /**
     * @return monster descrioption
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * Set the Healthpoints of the monster
     */
    public void setHP(int HP)
    {
        this.HP = HP;
    }
    /**
     * @return monster Healthpoints
     */
    public int getHP()
    {
        return HP;
    }
    /**
     * @return monster power
     */
    public int getPower()
    {
        return power;
    }
    
}
