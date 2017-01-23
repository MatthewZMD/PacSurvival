/* [Walker.java]
 * A Small program for antagonist settings and information
 * @author Matthew
 */

public class Walker extends Creature{

  //Declare Variables
  private int health;
  private long countTime;

    /**
     * default constructor Walker
     * @param x
     * @param y
     * @param dirX
     * @param dirY
     * @param health
     */
  Walker(double x, double y, double dirX, double dirY, double health) {
    super(x, y, dirX, dirY);
    updateHealth(health);
    MainGame.map[(int) y][(int) x] = MainGame.map[(int) y][(int) x]==3 ? 4:2;
    setCountTime(System.nanoTime());
  }

  /**
   * updateHealth
   * get class variable health
   * @param addHealth
   */
  public void updateHealth(double addHealth){
    this.health+=addHealth;
  }

  //******************Getters and setters**********/

  public double getHealth(){
    return this.health;
  }

  public long getCountTime() {
    return countTime;
  }

  public void setCountTime(long countTime) {
    this.countTime = countTime;
  }
}