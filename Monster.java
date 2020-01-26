
/**
 * class Monster - geef hier een beschrijving van deze class
 *
 * @author Jia wei Wang
 * @version 26-01-2020
 */
public class Monster
{
    private String name;
    private String description;
    private Room currentRoom;
    private int HP;
    private int power;
    private boolean agressive;

    /**
     * Constructor voor objects van class Monster
     */
    public Monster(String name, String description, int HP, int power, boolean agressive)
    {
        this.name = name;
        this.description = description;
        this.HP = HP;
        this.power = power;
        this.agressive = agressive;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setHP(int HP)
    {
        this.HP = HP;
    }
    
    public int getHP()
    {
        return HP;
    }
    
    public int getPower()
    {
        return power;
    }
    
    public boolean getAgressive()
    {
        return agressive;
    }
}
