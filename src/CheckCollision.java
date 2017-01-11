public class CheckCollision implements Runnable{

	public void run() {
//		ArrayList<Organism> organisms; 
		for(int i = 0; i < organisms.size(); i++){
			Organism o = organisms.get(i);
			
			if(player.getX() == o.getX() && player.getY() == o.getY()){
				if(o instanceof Walker){
					if(player.getPlantBuff()>0){
						System.out.println("You attacked the Walker at "+o.getX()+","+o.getY()+"!");
						((Walker) o).addHealth(-1);
					}else{
						player.addHealth(-1);
					}
				}else if(o instanceof Plant){
					player.addHealth(0.5);
					player.addPlantBuff(30);
					organisms.remove(i);
				}
			}
			
		}
		
	}
	
}
