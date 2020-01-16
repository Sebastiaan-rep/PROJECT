/**
 * Item.java. Bevat informatie over elk item in het spel.
 * 
 * @author Jia wei Wang
 */
public class Item
{
    private String description;
    private String name;

    /**
     * Constructor for objects of class Item
     */
    public Item(String newdescription)
    {
        // initialise instance variables
        description = newdescription;
    }

    public String getDescription()
    {
        return description;
    }

}
