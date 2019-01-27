package bgs99c.lab2;
import bgs99c.lab2.shared.*;


public abstract class Fighter extends FighterInfo{
    /**
     * Fighter's stats
     */
    public final static int HEALTH = 50, DEFENCE = 10, ACCURACY = 20, EVASION = 10, POWER = 5;
    /**
     * Returns moves that fighter could use
     */
    public Move[] getMoves(){
        throw new UnsupportedOperationException();
    }
    public void addType(Types t){
    }
    /**
     * Fighter's avatar
     */
    public String image;
    /**
     * Adds moves if there's not too many already (max = 5)
     * @param m New move
     */
    public void addMove(Move m){
        throw new UnsupportedOperationException();
    }
    /**
     * Returns accuracy considering debuff
     */
    public int getAccuracy(){
        throw new UnsupportedOperationException();
    }

    /**
     * Returns evasion considering debuff
     */
    public int getEvasion() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns power considering debuff
     */
    public int getPower() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates fighter with given name
     * @param n Name
     */
    public Fighter(String n){
    }

    /**
     * Changes fighter's stats and subtracts/adds ability points if you increase/decrease them
     * @param amount Amount the ability will be changed
     * @param stat Stat that will change
     */
    public void changeAbility(int amount, Stats stat){
        throw new UnsupportedOperationException();
    }

    /**
     * Returns current health considering damage in battle
     */
    public int getHealth(){
        throw new UnsupportedOperationException();
    }
    /**
     * Returns defence considering debuff
     */
    public int getDefence(){
        throw new UnsupportedOperationException();
    }
}
