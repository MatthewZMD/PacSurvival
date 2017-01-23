/* [CheckDeath.java]
 * A Small program for walker health information
 * @author Matthew & Jim
 */
public class CheckDeath implements Runnable {

    /**
     * CheckDeath thread that checks the death of the walkers continously
     */
    public synchronized void run() {
        //Run this method continuously
        while (true) {
            //Check all walkers
            for (int i = 0; i < MainGame.organisms.size(); i++) {
                Organism o = MainGame.organisms.get(i);
                if (o instanceof Walker) {
                    //Removes walker from arraylist if the walker's health hits 0
                    if (((Walker) o).getHealth() <= 0) {
                        MainGame.map[(int) o.getY()][(int) o.getX()] = MainGame.map[(int) o.getY()][(int) o.getX()] == 2 ? 0 : 3;
                        MainGame.organisms.remove(i);
                        System.out.println("Walker died");
                    }
                }
            }
        }
    }

}