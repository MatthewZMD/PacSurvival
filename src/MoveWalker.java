
public class MoveWalker implements Runnable{

	MoveWalker(){
		new Walker();
	}

	public void run() {

		for(int i = 0; i < Main.organisms.size(); i++) {
			if(Main.organisms.get(i) instanceof Walker){
				int randomNum = (int) (Math.random() * 4 + 1);
				if (randomNum == 1 && Main.map[(int) (Main.organisms.get(i).getX() + 1)][(int) Main.organisms.get(i).getY()] == 0) {
					Main.organisms.get(i).setX(Main.organisms.get(i).getX() + 1);
				}
				if (randomNum == 2) {
					if (Main.map[(int) (Main.organisms.get(i).getX() - 1)][(int) Main.organisms.get(i).getY()] == 0) {
						Main.organisms.get(i).setX(Main.organisms.get(i).getX() - 1);
					}
				}
				if (randomNum == 3) {
					if (Main.map[(int) Main.organisms.get(i).getX()][(int) Main.organisms.get(i).getY() + 1] == 0) {
						Main.organisms.get(i).setY(Main.organisms.get(i).getY() + 1);
					}
				} else {
					if (Main.map[(int) Main.organisms.get(i).getX()][(int) Main.organisms.get(i).getY() - 1] == 0) {
						Main.organisms.get(i).setY(Main.organisms.get(i).getY() - 1);
					}
				}
			}
		}
	}
	
}
