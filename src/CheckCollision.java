public class CheckCollision implements Runnable{

	public synchronized void run() {
		while(true){
//		System.out.println("Collisioning Size: "+MainGame.organisms.size());
			for(int i = 0; i < MainGame.organisms.size(); i++){
//				System.out.println("Checking Collision");
				Organism o = MainGame.organisms.get(i);

//                System.out.println(o.getX()+" "+o.getY()+" "+Math.floor(MainGame.player.getY())+" "+Math.floor(MainGame.player.getX()));
				if(Math.floor(MainGame.player.getY()) == o.getX() && Math.floor(MainGame.player.getX()) == o.getY()){
					if(o instanceof Walker){
                        if(MainGame.plantRemainTime>0){
							System.out.println("You attacked the Walker at "+o.getX()+","+o.getY()+"!");
							((Walker) o).updateHealth(-20);
						}else{
                            System.out.println("You collided with a Walker at "+o.getX()+","+o.getY());
                            MainGame.remainTime-=30;
						}
					}else if(o instanceof Plant){
						MainGame.remainTime+=100;
                        MainGame.plantRemainTime+=60;
                        System.out.println("You ate a plant at "+o.getX()+","+o.getY());
                        MainGame.map[(int) o.getY()][(int) o.getX()] = MainGame.map[(int) o.getY()][(int) o.getX()]==4 ? 2:0;
                        MainGame.organisms.remove(i);
					}
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
