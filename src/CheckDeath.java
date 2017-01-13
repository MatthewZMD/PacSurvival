public class CheckDeath implements Runnable{

	public void run(){
		for(int i = 0; i < Main.organisms.size(); i++){
			Organism o = Main.organisms.get(i);
			if(o instanceof Creature){
				if(((Creature) o).getHealth()<=0){
					Main.organisms.remove(i);
				}
			}
		}
	}
	
}
