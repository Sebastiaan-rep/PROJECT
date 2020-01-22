

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
    

    /**
     * Constructor for objects of class Item
     */
    public Item(String name, int weight, String description)
    {
        this.description = description;
        this.weight = weight;
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public String getName()
    {
        return name;
    }
}
