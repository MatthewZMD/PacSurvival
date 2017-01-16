import sun.applet.Main;

public class CheckDeath implements Runnable{

	public synchronized void run(){
		while(true){
//		System.out.println("Deathing Size: "+MainGame.organisms.size());
			for(int i = 0; i < MainGame.organisms.size(); i++){
				System.out.println("Checking Death");
				Organism o = MainGame.organisms.get(i);
				if(o instanceof Walker){
					if(((Walker) o).getHealth()<=0){
						MainGame.map[(int) o.getX()][(int) o.getY()] = MainGame.map[(int) o.getX()][(int) o.getY()] == 2 ? 0:3;
						MainGame.organisms.remove(i);
						System.out.println("Walker died");
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
