import sun.applet.Main;

public class WalkerAI implements Runnable{
	public void run() {
		while(true){
			for(int i = 0; i < MainGame.organisms.size(); i++) {
				if(MainGame.organisms.get(i) instanceof Walker){
					int randomNum = (int) (Math.random() * 4 + 1);
					int walkerX = (int) MainGame.organisms.get(i).getX();
					int walkerY = (int) MainGame.organisms.get(i).getY();
					MainGame.map[walkerY][walkerX] = MainGame.map[walkerY][walkerX] == 2 ? 0:3;
					if (randomNum == 1&&walkerY+1<MainGame.map.length){
						if(MainGame.map[walkerY + 1][walkerX] == 2){
							//breed
						}else if(MainGame.map[walkerY + 1][walkerX] != 1) {
							MainGame.organisms.get(i).setY(walkerY + 1);
						}
					}else if (randomNum == 2&&walkerY-1>=0) {
						if (MainGame.map[walkerY - 1][walkerX] != 1) {
							MainGame.organisms.get(i).setY(walkerY - 1);
						}
					}else if (randomNum == 3&& walkerX+1 < MainGame.map[0].length) {
						if (MainGame.map[walkerY][walkerX + 1] != 1) {
							MainGame.organisms.get(i).setX(walkerX + 1);
						}
					} else if (randomNum==4 && walkerX-1>=0){
						if (MainGame.map[walkerY][walkerX - 1] != 1) {
							MainGame.organisms.get(i).setX(walkerX - 1);
						}
					}
					walkerX = (int) MainGame.organisms.get(i).getX();
					walkerY = (int) MainGame.organisms.get(i).getY();
					MainGame.map[walkerY][walkerX] = MainGame.map[walkerY][walkerX] == 3 ? 4:2;

					if(MainGame.deltaSecond(((Walker) MainGame.organisms.get(i)).getCountTime())==1){
						((Walker) MainGame.organisms.get(i)).updateHealth(-1);
						((Walker) MainGame.organisms.get(i)).setCountTime(System.nanoTime());
					}

				}else if(MainGame.organisms.get(i) instanceof Plant){
					if(MainGame.deltaSecond(((Plant) MainGame.organisms.get(i)).getCountTime())==1){
						((Plant) MainGame.organisms.get(i)).updateHealth(-1);
						((Plant) MainGame.organisms.get(i)).setCountTime(System.nanoTime());
					}
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
}
