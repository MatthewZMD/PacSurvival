
public class MoveWalker implements Runnable{

	public void run() {
		Walker o = new Walker(0,0,0,0,0,0);
		// TODO Auto-generated method stub
		int randomNum = (int) (Math.random()*4+1);
		if(randomNum==1){
			if(Main.map[(int) (o.getX()+1)][(int) o.getY()]==0){
				o.setX(o.getX()+1);
			}
		}
		if(randomNum==2){
			if(Main.map[(int) (o.getX()-1)][(int) o.getY()]==0){
				o.setX(o.getX()-1);
			}
		}
		if(randomNum==3){
			if(Main.map[(int)o.getX()][(int) o.getY()+1]==0){
				o.setY(o.getY()+1);
			}
		}
		else{
			if(Main.map[(int)o.getX()][(int) o.getY()-1]==0){
				o.setY(o.getY()-1);
			}
		}
	}
	
}
