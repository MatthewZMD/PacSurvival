/* [Player.java]
 * A Small program for protagonist settings and information
 * @author Matthew
 */

public class Player extends Creature {

    //Declare Variables
    private int score;
    private String name;

    /**
     * Create a player object
     *
     * @param name of the player
     * @param x    position of the player
     * @param y    position of the player
     * @param dirX direction of the player
     * @param dirY direction of the player
     */
    Player(String name, double x, double y, double dirX, double dirY) {
        super(x, y, dirX, dirY);
        setName(name);
        this.score = 0;
    }

    /******************Getters and setters**********/
    /**
     * This sets the score of the player
     *
     * @param score of the player
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Get the score of the player
     *
     * @return score of the player
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Set the name of the player
     *
     * @param name of the player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the name of the player
     *
     * @return name of the player
     */
    public String getName() {
        return this.name;
    }

}