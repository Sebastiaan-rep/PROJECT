import java.util.HashSet;
import java.util.Random;

/**
 * class Player - Houdt de speler bij.
 *
 * @author Mathijs Slabbinck & ook een beetje Jonathan Dhoop
 * @version 29/05/2018
 */
public class Player
{
    private String naam;
    private Room currentRoom;
    private int hp;
    private int xp;
    private int power;
    Random rand = new Random();
    
    /**
     * maakt de speler klaar
     */
    public Player(String naam,Room currentRoom,int hp,int xp)
    {
        this.naam = naam;
        this.hp = hp;
        this.xp = xp;
    }
    public void setRoom(Room room){
        currentRoom = room;
    }
    public void getStronger(){
        int  n = rand.nextInt(2) + 1;
        if(n == 1)
        {
            hp++;
        }
        else
        {
            xp++;
        }
    }
    
    public int getAttack()
    {
        int  n = rand.nextInt(2) + 1;
        if(n == 1)
        {
            return 1;
        }
        return 0;
    }
    
    public int getSuperAttack()
    {
        int  n = rand.nextInt(3) + 1;
        if(n == 1)
        {
            return 3;
        }
        return 0; 
    }
   
    
    /*
    public boolean openRoom()
    {

        if (rugzak.contains())
        {
            rugzak.remove(item);
            currentRoom.addItem(item);
        }
    }
    */
    
    public int getHp()
    {
        return hp;
    }    
    
    public int getXp()
    {
        return xp;

    }
    
    public Room getRoom(){
        return currentRoom;
    }

}
