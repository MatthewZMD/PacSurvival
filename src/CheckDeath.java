import java.util.ArrayList;

public class CheckDeath implements Runnable{

	public void run(){
		ArrayList<Organism> organisms = null;
		for(int i = 0; i < organisms.size(); i++){
			Organism o = organisms.get(i);
			if(o instanceof Creature){
				if(((Creature) o).getHealth()<=0){
					organisms.remove(i);
				}
			}
		}
	}
	
}
