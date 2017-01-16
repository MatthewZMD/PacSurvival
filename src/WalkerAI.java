
public class WalkerAI implements Runnable{
	public void run() {
		while(true){
			for(int i = 0; i < MainGame.organisms.size(); i++) {
				if(MainGame.organisms.get(i) instanceof Walker){
					int randomNum = (int) (Math.random() * 4 + 1);
					if (randomNum == 1 && MainGame.map[(int) (MainGame.organisms.get(i).getX() + 1)][(int) MainGame.organisms.get(i).getY()] == 0) {
						MainGame.organisms.get(i).setX(MainGame.organisms.get(i).getX() + 1);
					}
					if (randomNum == 2) {
						if (MainGame.map[(int) (MainGame.organisms.get(i).getX() - 1)][(int) MainGame.organisms.get(i).getY()] == 0) {
							MainGame.organisms.get(i).setX(MainGame.organisms.get(i).getX() - 1);
						}
					}
					if (randomNum == 3) {
						if (MainGame.map[(int) MainGame.organisms.get(i).getX()][(int) MainGame.organisms.get(i).getY() + 1] == 0) {
							MainGame.organisms.get(i).setY(MainGame.organisms.get(i).getY() + 1);
						}
					} else {
						if (MainGame.map[(int) MainGame.organisms.get(i).getX()][(int) MainGame.organisms.get(i).getY() - 1] == 0) {
							MainGame.organisms.get(i).setY(MainGame.organisms.get(i).getY() - 1);
						}
					}
				}
			}
		}

	}
	
}
