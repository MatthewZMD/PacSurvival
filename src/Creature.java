/* [Creature.java]
 * A Small program for creature settings and information
 * @author Matthew
 */

abstract public class Creature extends Organism{

  //Declare private class variables
  private double dirX, dirY;

  /**
   * default class constructor Creature
   * @param x
   * @param y
   * @param dirX
   * @param dirY
   */
  public Creature(double x, double y, double dirX, double dirY){
      //assign variable values
      super(x, y);
      setDirX(dirX);
      setDirY(dirY);
  }

  /*************Getters and Setters*********/

  public double getDirX() {
    return dirX;
  }

  public void setDirX(double dirX) {
    this.dirX = dirX;
  }

  public double getDirY() {
    return dirY;
  }

  public void setDirY(double dirY) {
    this.dirY = dirY;
  }


}