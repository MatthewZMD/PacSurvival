/* [Plant.java]
 * A Small program for Plant settings and information
 * @author Matthew
 */

public class Plant extends Organism{

	/**
	* extended constructor
	* sets the variables in origin class and sets others in this class
	* @param int x, int y
	* @return void
	*/
	Plant(int x, int y){
		super(x, y);
		MainGame.map[(int) x][(int) y] = MainGame.map[(int) x][(int) y]==2 ? 4:3;
	}
	
}
