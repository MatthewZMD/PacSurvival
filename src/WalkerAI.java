
public class WalkerAI implements Runnable{
	public void run() {
		while(true){
			for(int i = 0; i < MainGame.organisms.size(); i++) {
				if(MainGame.organisms.get(i) instanceof Walker){
					int randomNum = (int) (Math.random() * 4 + 1);
					int walkerX = (int) MainGame.organisms.get(i).getX();
					int walkerY = (int) MainGame.organisms.get(i).getY();
					MainGame.map[walkerX][walkerY] = MainGame.map[walkerX][walkerY] == 2 ? 0:3;

					if (randomNum == 1 && MainGame.map[walkerX + 1][walkerY] != 1) {
						MainGame.organisms.get(i).setX(walkerX + 1);
					}else if (randomNum == 2) {
						if (MainGame.map[walkerX - 1][walkerY] != 1) {
							MainGame.organisms.get(i).setX(walkerX - 1);
						}
					}else if (randomNum == 3) {
						if (MainGame.map[walkerX][walkerY + 1] != 1) {
							MainGame.organisms.get(i).setY(walkerY + 1);
						}
					} else {
						if (MainGame.map[walkerX][walkerY - 1] != 1) {
							MainGame.organisms.get(i).setY(walkerY - 1);
						}
					}
					walkerX = (int) MainGame.organisms.get(i).getX();
					walkerY = (int) MainGame.organisms.get(i).getY();
					MainGame.map[walkerX][walkerY] = MainGame.map[walkerX][walkerY] == 3 ? 4:2;

				}
			}
		}

	}
	
}
