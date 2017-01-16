public class CheckCollision implements Runnable{

	public synchronized void run() {
		while(true){
//		System.out.println("Collisioning Size: "+Main.organisms.size());
			for(int i = 0; i < Main.organisms.size(); i++){
				System.out.println("Checking Collision");
				Organism o = Main.organisms.get(i);
				if(Main.player.getX() == o.getX() && Main.player.getY() == o.getY()){
					if(o instanceof Walker){
						if(Main.player.getPlantBuff()>0){
							System.out.println("You attacked the Walker at "+o.getX()+","+o.getY()+"!");
							((Walker) o).updateHealth(-1);
						}else{
							Main.remainTime-=30;
						}
					}else if(o instanceof Plant){
						Main.remainTime+=60;
						Main.organisms.remove(i);
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
