/* [Creature.java]
 * A Small program for creature settings and information
 * @author Jim
 */

abstract public class Creature extends Organism{

	private double dirX, dirY, health=0;
	private boolean alive = true;
	
	public Creature(double x, double y, double dirX, double dirY, double health){
		super(x, y);
		setDirX(dirX);
	    setDirY(dirY);
	    updateHealth(health);
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
	
	/**
	* setHealth
	* get class variable health
	* @param int heatlth
	*/
	public void addHealth(double newHealth){
		this.health+=newHealth;
	}
	
	/**
	* getHealth
	* get class variable health
	* @return  int health class variable
	*/
	public double getHealth(){
		return this.health;
	}
	
}
