/* [Walker.java]
 * A Small program for leaderboard information
 * @author Matthew & Jim
 */

public class WalkerAI implements Runnable {

    /**
     * run
     * moves the walker position
     */
    public void run() {
        //Loop continuously
        while (true) {
            //Run for all walkers at once
            for (int i = 0; i < MainGame.organisms.size(); i++) {
                if (MainGame.organisms.get(i) instanceof Walker) {
                    //Get random number for walker to determine direction
                    int randomNum = (int) (Math.random() * 4 + 1);
                    int walkerX = (int) MainGame.organisms.get(i).getX();
                    int walkerY = (int) MainGame.organisms.get(i).getY();
                    //Walker start moving, change the block to blank or plant
                    MainGame.map[walkerY][walkerX] = MainGame.map[walkerY][walkerX] == 2 ? 0 : 3;

                    //Set walker's movement direction based on random number and move the walker if it can move in that direction.
                    if (randomNum == 1 && walkerY + 1 < MainGame.map.length && MainGame.map[walkerY + 1][walkerX] != 1) {
                        MainGame.organisms.get(i).setY(walkerY + 1);
                    } else if (randomNum == 2 && walkerY - 1 >= 0 && MainGame.map[walkerY - 1][walkerX] != 1) {
                        MainGame.organisms.get(i).setY(walkerY - 1);
                    } else if (randomNum == 3 && walkerX + 1 < MainGame.map[0].length && MainGame.map[walkerY][walkerX + 1] != 1) {
                        MainGame.organisms.get(i).setX(walkerX + 1);
                    } else if (randomNum == 4 && walkerX - 1 >= 0 && MainGame.map[walkerY][walkerX - 1] != 1) {
                        MainGame.organisms.get(i).setX(walkerX - 1);
                    }

                    //Get the new position of the walker
                    walkerX = (int) MainGame.organisms.get(i).getX();
                    walkerY = (int) MainGame.organisms.get(i).getY();
                    //Apply changes to the array
                    MainGame.map[walkerY][walkerX] = MainGame.map[walkerY][walkerX] == 3 ? 4 : 2;

                    //Decrease health every second
                    if (MainGame.deltaSecond(((Walker) MainGame.organisms.get(i)).getCountTime()) == 1) {
                        ((Walker) MainGame.organisms.get(i)).updateHealth(-1);
                        ((Walker) MainGame.organisms.get(i)).setCountTime(System.nanoTime());
                    }

                } else if (MainGame.organisms.get(i) instanceof Plant) {
                    //Decrease health every second
                    if (MainGame.deltaSecond(((Plant) MainGame.organisms.get(i)).getCountTime()) == 1) {
                        ((Plant) MainGame.organisms.get(i)).updateHealth(-1);
                        ((Plant) MainGame.organisms.get(i)).setCountTime(System.nanoTime());
                    }
                }
            }
            //Move walkers every second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}