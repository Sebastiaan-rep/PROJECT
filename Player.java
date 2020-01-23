import java.util.Set;
import java.util.*;
import java.util.ArrayList;
/**
 * The Player class stores all the information about the player.
 *
 * @author Kristiyan Parlikov & Marek Muzik
 * @version 1.0
 * @since 2019-10-19
 */
public class Player
{
    /**
     * Fields.
     */
    private int weight;
    private int health;
    private int power;
    private boolean alive;
    private Item reward;
    private ArrayList <Item> playerInventory;
    private HashMap<String, Item> inventory;

    /**
     * Constructor for objects of class Player.
     */
    public Player(int weight, int health)
    {
        this.weight = weight;
        this.health = health;
        power = 1;
        alive = true;
        playerInventory = new ArrayList<Item>();
    }

    /**
     * This method is used when the player is killed.
     */
    public void die()
    {
        alive = false;
    }

    /**
     * This method sets the power of the player.
     * @param newPower The changed power of the player.
     */
    public void setPower(int newPower)
    {
        power = newPower;
    }

    /**
     * This method adds an earned item from a monster kill to the inventory
     * @param newItem The "to be added" item.
     */
    public void addItemToInventory(Item newItem, String hallwayReward)
    {
        playerInventory.add(newItem);
        System.out.println("Your reward:" + hallwayReward +" has been added your inventory");
    }

    /**
     * This method gives information about if the player is alive or
     * dead.
     */
    public boolean getAlive(){
        return alive;
    }

    /**
     * This method returns the power of the player.
     */
    public int getPower(){
        return power;
    }

    /** 
     * This method returns the player's items in the inventory.
     */
    public ArrayList getPlayerInventory(){
        return playerInventory;
    }

}
