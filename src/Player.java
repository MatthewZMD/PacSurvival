/**
 * Created by Matthew on 2016-12-22.
 */
public class Player extends GameObject {
    private double dirX;
    private double dirY;
    public Player(double x, double y,double dirX,double dirY) {
        super(x, y);
        setDirX(dirX);
        setDirY(dirY);
    }

    public double getDirX() {
        return dirX;
    }

    public void setDirX(double dirX) {
        this.dirX = dirX;
    }

    public double getDirY() {
        return dirY;
    }

    public void setDirY(double dirY) {
        this.dirY = dirY;
    }
}
