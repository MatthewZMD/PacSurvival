/* [CheckCollision.java]
 * A Small program to check position information
 * @author Matthew & Jim
 */

public class CheckCollision implements Runnable{

  //Declare class public variables
  public static double collideTime, attackTime, walkerDiedTime, plantReceivedTime, fakePlantReceivedTime;

  /**run
   * runs the thread in parallel to [MainGame.java]
   */
  public synchronized void run() {
    while(true){
      //Check if collision happens with fake plants
      if(MainGame.map[(int) MainGame.player.getX()][(int) MainGame.player.getY()]==3){
          for(int i = 0; i < MainGame.organisms.size(); i++){
              Organism o = MainGame.organisms.get(i);
              if(i==MainGame.organisms.size()-1){
//                  fakePlantReceivedTime=3;
                System.out.println("Fake plant");
                  MainGame.map[(int) MainGame.player.getX()][(int) MainGame.player.getY()]=0;
              }
          }
      }
      //Check all organisms for collisions
      for(int i = 0; i < MainGame.organisms.size(); i++){
        Organism o = MainGame.organisms.get(i);
        if (o!=null&&Math.floor(MainGame.player.getY()) >= o.getX()-0.5 && Math.floor(MainGame.player.getY()) <= o.getX()+0.5 && Math.floor(MainGame.player.getX()) >= o.getY()-0.5 && Math.floor(MainGame.player.getX()) <= o.getY()+0.5) {
          //Check if collided with walker
          if (o instanceof Walker) {
            //Player attacks walker with buff in this case
            if (MainGame.plantRemainTime > 0) {
              System.out.print("You attacked the Walker at " + o.getX() + "," + o.getY() + "! ");
              ((Walker) o).updateHealth(-100);
              System.out.println("Walker remaining health: "+((Walker) o).getHealth());
              attackTime=3;
              //Player kills walker in below case
              if(((Walker) o).getHealth()<=0){
                //Case where the walker dies from too many player attack
                MainGame.map[(int) o.getY()][(int) o.getX()] = MainGame.map[(int) o.getY()][(int) o.getX()] == 2 ? 0:3;
                MainGame.organisms.remove(i);
                System.out.println("Walker died");
                walkerDiedTime=3;
              }
              //Player collides with walker without buff in below case
            } else {
              System.out.println("You collided with a Walker at " + o.getX() + "," + o.getY());
              collideTime = 3;
              MainGame.remainTime -= 30;
            }
            //Player receives the real plant in below case
          } else if (o instanceof Plant) {
            MainGame.remainTime += 100;
            MainGame.plantRemainTime += 60;
            System.out.println("You ate a plant at " + o.getX() + "," + o.getY());
            MainGame.map[(int) o.getY()][(int) o.getX()] = MainGame.map[(int) o.getY()][(int) o.getX()] == 4 ? 2 : 0;
            MainGame.organisms.remove(i);
            plantReceivedTime=3;
          }
          //Pause the algorithm for some time
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }

      }
      try{
        //Thread.sleep(10);
        Thread.yield();
      }catch(Exception e) {
        e.printStackTrace();
      }
    }

  }

}