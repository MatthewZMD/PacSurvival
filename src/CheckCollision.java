public class CheckCollision implements Runnable{

	public synchronized void run() {
		while(true){
//		System.out.println("Collisioning Size: "+MainGame.organisms.size());
			for(int i = 0; i < MainGame.organisms.size(); i++){
				System.out.println("Checking Collision");
				Organism o = MainGame.organisms.get(i);

				if(MainGame.player.getX() == o.getX() && MainGame.player.getY() == o.getY()){
					if(o instanceof Walker){
						if(MainGame.plantRemainTime>0){
							System.out.println("You attacked the Walker at "+o.getX()+","+o.getY()+"!");
							((Walker) o).updateHealth(-1);
						}else{
							MainGame.remainTime-=30;
						}
					}else if(o instanceof Plant){
						MainGame.remainTime+=60;
                        MainGame.plantRemainTime+=20;
                        MainGame.map[(int) o.getX()][(int) o.getY()] = MainGame.map[(int) o.getX()][(int) o.getY()]==4 ? 2:0;
                        MainGame.organisms.remove(i);
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
