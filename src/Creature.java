/* [Creature.java]
 * A Small program for creature settings and information
 * @author Matthew
 */

abstract public class Creature extends Organism {

    //Declare private class variables
    private double dirX, dirY;

    /**
     * Create a creature object
     *
     * @param x    position
     * @param y    position
     * @param dirX direction
     * @param dirY direction
     */
    public Creature(double x, double y, double dirX, double dirY) {
        //assign variable values
        super(x, y);
        setDirX(dirX);
        setDirY(dirY);
    }

    /*************Getters and Setters*********/

    /**
     * Get the x direction
     *
     * @return x direction
     */
    public double getDirX() {
        return dirX;
    }

    /**
     * Set the x direction
     *
     * @param dirX x direction
     */
    public void setDirX(double dirX) {
        this.dirX = dirX;
    }

    /**
     * Get the y direction
     *
     * @return y direction
     */
    public double getDirY() {
        return dirY;
    }

    /**
     * Set the y direction
     *
     * @param dirY y direction
     */
    public void setDirY(double dirY) {
        this.dirY = dirY;
    }


}