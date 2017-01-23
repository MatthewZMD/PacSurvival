/* [Organism.java]
 * A Small program for object settings and information
 * @author Jim
 */

abstract public class Organism {

    //Declare Variables
    private double x, y;

    /**
     * Create a organism object
     *
     * @param x
     * @param y
     */
    public Organism(double x, double y) {
        //Set x,y position
        setX(x);
        setY(y);
    }

    /**
     * This method assigns class variable x in a public method
     *
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * This method assigns class variable y in a public method
     *
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Get x position
     *
     * @return x position
     */
    public double getX() {
        return this.x;
    }

    /**
     * Get y position
     *
     * @return y position
     */
    public double getY() {
        return this.y;
    }

}