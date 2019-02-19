package bgs99c.lab2;


/**
 * Class of a tournament/battle participant. You should extend this with your own player class.
 */
public abstract class Player {
    private Strategy strategy;
    Strategy getStrategy(){
        return strategy;
    }
    @Override
    public final int hashCode() {
    	return super.hashCode();
    }
    /**
     * Creates player with given name and strategy
     * @param n Name
     * @param s Strategy
     */
    public Player(String n, Strategy s){
        name = n;
        strategy = s;
        fought = false;
    }
    boolean fought;
    Fighter[] fighters;
    private String name;
    public String getName() {
    	return name;
    }
    public String toString(){
        return "Player " + name;
    }
}
