/* [Player.java]
 * A Small program for protagonist settings and information
 * @author Jim
 */

public class Player extends Creature{

	//Declare Variables
	private int score;
	private String name;
	
	private int plantBuff = 0;
	
	/**
	* default constructor
	* sets the variables of protagonist
	* @param int x, int y, String name
	* @return void
	*/
	Player(double x, double y, double dirX, double dirY, double health, String name){
		super(x, y, dirX, dirY, health);
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

	public int getPlantBuff() {
		return plantBuff;
	}

	public void addPlantBuff(int newBuff) {
		this.plantBuff+=newBuff;
	}
}
