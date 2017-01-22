/* [Player.java]
 * A Small program for protagonist settings and information
 * @author Matthew
 */

public class Player extends Creature{

  //Declare Variables
  private int score;
  private String name;

  /**
   * default constructor
   * sets the variables of protagonist
   * @param x, y, String name
   * @return void
   */
  Player(String name,double x, double y, double dirX, double dirY){
    super(x, y, dirX, dirY);
    setName(name);
    this.score = 0;
  }

  public void setScore(int score){
    this.score = score;
  }

  public int getScore(){
    return this.score;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getName(){
    return this.name;
  }

}