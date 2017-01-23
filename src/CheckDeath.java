/* [CheckDeath.java]
 * A Small program for walker health information
 * @author Matthew & Jim
 */
public class CheckDeath implements Runnable{

  /**run
   * runs an algorithm method in the same time as [MainGame.java]
   */
  public synchronized void run(){
    while(true){
//		System.out.println("Deathing Size: "+MainGame.organisms.size());
      //Check all walkers
      for(int i = 0; i < MainGame.organisms.size(); i++){
//				System.out.println("Checking Death");
        Organism o = MainGame.organisms.get(i);
        if(o instanceof Walker){
          //Removes walker from arraylist if the walker's health hits 0
          if(((Walker) o).getHealth()<=0){
            MainGame.map[(int) o.getY()][(int) o.getX()] = MainGame.map[(int) o.getY()][(int) o.getX()] == 2 ? 0:3;
            MainGame.organisms.remove(i);
            System.out.println("Walker died");
          }
        }
      }
    }
  }

}