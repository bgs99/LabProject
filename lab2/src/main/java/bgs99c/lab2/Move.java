package bgs99c.lab2;
import bgs99c.lab2.shared.*;
public abstract class Move implements Action{
    /**
     * Amount of points you can spend on effects
     */
    public static final int ABLPOINTS = 20;
    private int abilityPoints = ABLPOINTS;

    /**
     * Returns amount of ability points left
     */
    public int pointsLeft(){
        return abilityPoints;
    }
    void spendPoints(int amount){
        abilityPoints -= amount;
    }
    boolean checkPoints(int amount){
        if(pointsLeft()<amount){
            OutputLogger.log(this.getClass().getSimpleName() + "is too powerful");
            return false;
        }
        return true;
    }
    final static double checkEffect(Types ability, Types receiver){
        if(ability.goodAgainst(receiver))
            return 2;
        if(ability.badAgainst(receiver))
            return 0.5;
        return 1;
    }
}