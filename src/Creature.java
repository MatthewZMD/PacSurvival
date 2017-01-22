/* [Creature.java]
 * A Small program for creature settings and information
 * @author Matthew
 */

abstract public class Creature extends Organism{

  private double dirX, dirY;

  public Creature(double x, double y, double dirX, double dirY){
      //assign variable values
      super(x, y);
      setDirX(dirX);
      setDirY(dirY);
  }

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