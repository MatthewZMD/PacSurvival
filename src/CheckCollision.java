
public class CheckCollision implements Runnable{

	public void run() {
		
		for(int i = 0; i < organisms.size(); i++){
			Organism o = organisms.get(i);
			
			if(player.getX() == o.getX() && player.getY() == o.getY()){
				if(o instanceof Walker){
					if(player.getPlantBuff()>0){
						System.out.println("You attacked the Walker at "+o.getX()+","+o.getY()+"!");
						
					}else{
						player.addHealth(-1);
					}
				}
			}
			
		}
		
	}
	
}
