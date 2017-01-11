/* [Organism.java]
 * A Small program for object settings and information
 * @author Jim
 */

abstract public class Organism {
	
	//Declare Variables
	private double x, y;
	
	/**
	* default constructor
	* sets the variables
	* @param int x, int y
	* @return void
	*/
	public Organism(double x, double y){
		setX(x);
		setY(y);
	}
	
	/**
	 * setX
	 * This method assigns class variable x in a public method
	 * @param int x
	 * @return none
	 */
	public void setX(double x){
		this.x = x;
	}
	
	/**
	 * setY
	 * This method assigns class variable y in a public method
	 * @param int y
	 * @return none
	 */
	public void setY(double y){
		this.y = y;
	}
	
	/**
	* getX
	* get class variable x
	* @param void
	* @return  int x class variable
	*/
	public double getX(){
		return this.x;
	}
	
	/**
	* getY
	* get class variable y
	* @param void
	* @return  int y class variable
	*/
	public double getY(){
		return this.y;
	}
	
}
