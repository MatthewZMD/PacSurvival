public class CheckDeath implements Runnable{

	public synchronized void run(){
		while(true){
//		System.out.println("Deathing Size: "+Main.organisms.size());
			for(int i = 0; i < Main.organisms.size(); i++){
				System.out.println("Checking Death");
				Organism o = Main.organisms.get(i);
				if(o instanceof Walker){
					if(((Walker) o).getHealth()<=0){
						Main.organisms.remove(i);
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
