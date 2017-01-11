
public class MoveWalker implements Runnable{

	public void run() {
		Walker o;
		// TODO Auto-generated method stub
		int randomNum = (int) (Math.random()*4+1);
		if(randomNum==1){
			if(map[(int) (o.getX()+1)][(int) o.getY()]==0){
				o.setX(o.getX()+1);
			}
		}
		if(randomNum==2){
			if(map[(int) (o.getX()-1)][(int) o.getY()]==0){
				o.setX(o.getX()-1);
			}
		}
		if(randomNum==3){
			if(map[(int)o.getX()][(int) o.getY()+1]==0){
				o.setY(o.getY()+1);
			}
		}
		else{
			if(map[(int)o.getX()][(int) o.getY()-1]==0){
				o.setY(o.getY()-1);
			}
		}
	}
	
}
