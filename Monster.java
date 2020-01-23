
/**
 * The Monster class manages all the monsters in the game.
 *
 * @author Kristiyan Parlikov & Marek Muzik
 * @version 1.0
 * @since 2019-10-19
 */
public class Monster
{
    /**
     * Fields.
     */
    private String name;
    private int hitPoints;
    private boolean alive;
    private Item reward;

    /**
     * Constructor for objects of class Monster
     * @param name The name of the monster.
     * @param hitPoints The health level of the monster.
     */
    public Monster(String name, int hitPoints)
    {
        alive = true;
        this.hitPoints = hitPoints;
        this.name = name;

    }

    /**
     * This method gets the name of the monster.
     */
    public String getName()
    {
        return name;
    }

    /**
     * This method is used when the monster is killed.
     */
    public void die(){
        alive=false;
    }

    /**
     * This method gives information about if the monster is alive or
     * already killed.
     */
    public boolean getAlive(){
        return alive;
    }

    /**
     * This method sets the reward from the monster.
     * @param reward The new reward.
     */
    public void setItem(Item reward)
    {
        this.reward = reward;
    }

    /**
     * This method prints the infromation about the monster.
     * @param currentRoomMonster This shows which is the monster required
     * the information for.
     * @param roomReward This shows which is the reward you can get by 
     * killing the moster.
     */
    public void printMonsterInfo(Monster currentRoomMonster, Item roomReward)
    {
        Monster monster = currentRoomMonster;
        Item reward = roomReward;
        if(alive == true){
            System.out.println("Watch OUT! Here is the " + monster.getName() 
                + " which guards a " + reward.getName());
            System.out.println("Monster has " + hitPoints + " hitpoints!");

        }else{
            System.out.println(monster + " is already killed");
        }
    }
}