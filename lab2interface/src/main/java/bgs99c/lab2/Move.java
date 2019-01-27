package bgs99c.lab2;
public abstract class Move implements Action {
	/**
	 * Package-private constructor to prevent extension
	 */
	Move(){}
    /**
     * Amount of points you can spend on effects
     */
    public static final int ABLPOINTS = 20;

    /**
     * Returns amount of ability points left
     */
    public int pointsLeft(){
        throw new UnsupportedOperationException();
    }

    public Log apply(Battle battle) {
        throw new UnsupportedOperationException();
    }
}
