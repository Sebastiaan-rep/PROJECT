
import java.util.ArrayList;
/**
 * Item.java. Bevat informatie over elk item in het spel.
 * 
 * @author Jia wei Wang
 */
public class Item
{
    public String description;
    public String name;
    public int weight;
    private int power;
    

    /**
     * Constructor for objects of class Item
     */
    public Item(String name, int weight, String description)
    {
        this.description = description;
        this.weight = weight;
        this.name = name;
    }
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, int power)
    {
        this.name= name;
        this.power=power;
    }
    /*
     * Gets description of item
     */
    public String getDescription()
    {
        return description;
    }
    /*
     * Gets weight of item
     */
    public int getWeight()
    {
        return weight;
    }
    /*
     * Gets name of item
     */
    public String getName()
    {
        return name;
    }
}
