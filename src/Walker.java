/* [Walker.java]
 * A Small program for antagonist settings and information
 * @author Matthew
 */

public class Walker extends Creature{
	
	//Declare Variables
	private int gender,health;
	
	Walker(double x, double y, double dirX, double dirY, double health, int gender) {
		super(x, y, dirX, dirY);
		setGender(gender);
		updateHealth(health);
		MainGame.map[(int) x][(int) y] = MainGame.map[(int) x][(int) y]==3 ? 4:2;
	}
	
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public int getGender(){
		return this.gender;
	}

	public boolean compareCoordinates(Walker one, Walker two){
		if(one.getGender() == two.getGender() && one.getX() == two.getX() && two.getY() == one.getY()){
			return true;
		}else{
			return false;
		}
	}
	
	public void makeBabies(){
		int x,y;
		do{
			x = (int) (Math.random()*MainGame.map[0].length);
			y = (int) (Math.random()*MainGame.map.length);
		}while(MainGame.map[x][y]!=0);
		
		int gender = (int) (Math.random()*2+1);
		new Walker(x, y, -1, 0, 3, gender);
	}

	/**
	 * updateHealth
	 * get class variable health
	 * @param newHealth
	 */
	public void updateHealth(double newHealth){
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