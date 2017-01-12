import java.util.ArrayList;

public class CheckCollision implements Runnable{

	public void run() {
		ArrayList<Organism> organisms = null;
		for(int i = 0; i < organisms.size(); i++){
			Organism o = organisms.get(i);
			
			if(Main.player.getX() == o.getX() && Main.player.getY() == o.getY()){
				if(o instanceof Walker){
					if(Main.player.getPlantBuff()>0){
						System.out.println("You attacked the Walker at "+o.getX()+","+o.getY()+"!");
						((Walker) o).updateHealth(-1);
					}else{
						Main.player.updateHealth(-1);
					}
				}else if(o instanceof Plant){
					Main.player.updateHealth(0.5);
					Main.player.addPlantBuff(30);
					organisms.remove(i);
				}
			}
			
		}
		
	}
	
}
