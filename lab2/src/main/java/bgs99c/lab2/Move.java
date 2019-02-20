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
    public final int pointsLeft(){
        return abilityPoints;
    }
    final void spendPoints(int amount){
        abilityPoints -= amount;
    }
    final boolean checkPoints(int amount){
        if(pointsLeft() < amount){
            throw new OPError();
        }
        return true;
    }
    static double checkEffect(Types ability, Types receiver){
        return ability.getEfficiency(receiver);
    }
}
