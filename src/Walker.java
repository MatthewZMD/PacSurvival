/* [Walker.java]
 * A Small program for antagonist settings and information
 * @author Matthew
 */

public class Walker extends Creature{

  //Declare Variables
  private int gender,health;
  private long countTime;

  Walker(double x, double y, double dirX, double dirY, double health, int gender) {
    super(x, y, dirX, dirY);
    setGender(gender);
    updateHealth(health);
    MainGame.map[(int) y][(int) x] = MainGame.map[(int) y][(int) x]==3 ? 4:2;
    setCountTime(System.nanoTime());
  }

  public void setGender(int gender) {
    this.gender = gender;
  }

  public int getGender(){
    return this.gender;
  }

  /**
   * updateHealth
   * get class variable health
   * @param addHealth
   */
  public void updateHealth(double addHealth){
    this.health+=addHealth;
  }

  /**
   * getHealth
   * get class variable health
   * @return  int health class variable
   */
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