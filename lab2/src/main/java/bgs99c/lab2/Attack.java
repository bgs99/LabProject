package bgs99c.lab2;
import bgs99c.lab2.shared.Types;
import bgs99c.lab2.shared.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Attack extends Move{
    public final static int DEFAULT_ACCURACY = 40;
    private int accuracy = DEFAULT_ACCURACY;
    private Types type;
    /**
     * Creates attack with given type
     * @param t Type of attack
     */
    public Attack(Types t){
        type = t;
    }

    /**
     * Changes accuracy and substracts/adds points if amount is positive/negative
     * @param amount Amount the accuracy will change. Positive to increase, negative to decrease
     */
    protected final void changeAccuracy(int amount){
        if(checkPoints(amount)){
            accuracy+=amount;
        }
    }
    private List<Effect> effects = new ArrayList<>();

    /**
     * Adds effect to attack if there's enough point for that
     * @param e Effect to add
     */
    protected final void addEffect(Effect e){
        if(checkPoints(e.cost())){
            spendPoints(e.cost());
            effects.add(e);
        }
    }

    /**
     * Effect of attack
     */
    public final class Effect{
        /**
         * Creates effect with given type and level
         * @param t Type of effect
         * @param v Level of effect
         */
        public Effect(EffectType t, int v){
            type = t;
            value = v;
        }
        EffectType type;
        int value;
        final int cost(){
            switch (type){
                case STUN:
                    return 10 * value;
                case LEECH:
                    return 4 * value;
                case STATS:
                    return 12 * value;
                case PERIODIC:
                    return 5 * value;
                case DAMAGE:
                    return value;
                default:
                    return 0;
            }
        }
    }

    /**
     * Description wich will be displayed when attack is used
     * @param f User of attack
     * @param d Target of attack
     */
    public abstract String description(Fighter f, FighterInfo d);

    /**
     * Type of attack effect
     */
    public enum EffectType{
        /**
         * Applies raw damage
         */
        DAMAGE,
        /**
         * Adds periodic damage that gradually wears off
         */
        PERIODIC,
        /**
         * Damages target and heals user for a part of that damage
         */
        LEECH,
        /**
         * Stuns target
         */
        STUN,
        /**
         * Adds debuff to target's stats
         */
        STATS
    }
    private boolean rollDice(Fighter attack, FighterInfo defence){
        Random r = new Random();
        int v = r.nextInt(100);
        if (v > accuracy + attack.getAccuracy() - defence.getEvasion()){
            OutputLogger.log(defence + " evaded " + attack + "'s attack");
            return false;
        }
        return true;
    }
    public Log apply(Battle b){
        if(!Arrays.asList(b.currentFighter().getMoves()).contains(this)){
            OutputLogger.log(b.currentFighter() + " doesn't know how to use " + this);
            return Log.Fail(b.currentFighter(),b.getCurrentPlayer());
        }
        OutputLogger.log(description(b.currentFighter(), b.currentOpponent()));
        if(!rollDice(b.currentFighter(), b.currentOpponent())){
            return Log.Fail(b.currentFighter(),b.getCurrentPlayer());
        }
        int d = 0, s = 0, l = 0, n = 0, p = 0;
        for(Effect e : effects){
            double valueK = 1;
            for(Types t : b.currentOpponent().getTypes())
                valueK *= checkEffect(type, t);
            int value = (int)(e.value*valueK);
            switch (e.type){
                case DAMAGE:
                    d += b.currentOpponent().applyDamage(value + b.currentFighter().getPower());
                    break;
                case STATS:
                    s+=value;
                    b.currentOpponent().lowerStats(value);
                    break;
                case LEECH:
                    int dmg = b.currentOpponent().applyDamage(value);
                    b.currentFighter().heal(dmg);
                    l+=dmg;
                    break;
                case STUN:
                    b.currentOpponent().addStun(value);
                    n+=value;
                    break;
                case PERIODIC:
                    b.currentOpponent().addPeriodicDamage(value);
                    p += value;
                    break;
            }
        }
        return new AttackLog(b.currentFighter(),new AttackResult(d,s,l,n,p), b.currentOpponent(),b.getCurrentPlayer());
    }
}
