/* [Walker.java]
 * A Small program for antagonist settings and information
 * @author Matthew
 */

public class Walker extends Creature {

    //Declare Variables
    private int health;
    private long countTime;

    /**
     * Create a walker object
     *
     * @param x      position
     * @param y      position
     * @param dirX   direction
     * @param dirY   direction
     * @param health
     */
    Walker(double x, double y, double dirX, double dirY, double health) {
        super(x, y, dirX, dirY);
        updateHealth(health);
        //Add the walker's position to the array
        MainGame.map[(int) y][(int) x] = MainGame.map[(int) y][(int) x] == 3 ? 4 : 2;
        setCountTime(System.nanoTime());
    }

    /**
     * updateHealth
     * additional health to the walker
     *
     * @param addHealth additional health
     */
    public void updateHealth(double addHealth) {
        this.health += addHealth;
    }

    //******************Getters and setters**********/

    public double getHealth() {
        return this.health;
    }

    /**
     * Get the time counted
     *
     * @return the time counted
     */
    public long getCountTime() {
        return countTime;
    }

    /**
     * Set counted time
     *
     * @param countTime counted time
     */
    public void setCountTime(long countTime) {
        this.countTime = countTime;
    }
}