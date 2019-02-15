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
            spendPoints(amount);
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
            return type.price*value;
        }
    }

    /**
     * Description to be displayed when attack is used
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
        DAMAGE(1, true),
        /**
         * Adds periodic damage that gradually wears off
         */
        PERIODIC(5, true),
        /**
         * Damages target and heals user for a part of that damage
         */
        LEECH(6, true),
        /**
         * Stuns target
         */
        STUN(10, true),
        /**
         * Heals target
         */
        HEAL(4, false),
        /**
         * Adds debuff to target's stats
         */
        STATS_DOWN(12, true),
        /**
         * Adds buff to your own stats
         */
        STATS_UP(12, false);

        int price;
        boolean negative;
        EffectType(int price, boolean negative) {
            this.price = price;
            this.negative = negative;
        }
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
    public final Log apply(Battle b){
        if(! Arrays.asList(b.currentFighter().getMoves()).contains(this)) {
            OutputLogger.log(b.currentFighter() + " doesn't know how to use " + this);
            return Log.Fail(b.currentFighter(),b.getCurrentPlayer());
        }

        OutputLogger.log(description(b.currentFighter(), b.currentOpponent()));

        if(!rollDice(b.currentFighter(), b.currentOpponent())){
            return Log.Fail(b.currentFighter(),b.getCurrentPlayer());
        }

        int damageResult    = 0;
        int debuffResult    = 0;
        int stunResult      = 0;
        int periodicResult  = 0;
        int healResult      = 0;
        int buffResult      = 0;

        for(Effect e : effects){

            double valueK = 1;

            FighterInfo target = e.type.negative ? b.currentOpponent() : b.currentFighter();

            for(Types t : target.getTypes())
                valueK *= checkEffect(type, t);

            if(valueK >=2 ) {
                OutputLogger.log("It's super effective!");
            } else if(valueK <= 0.5) {
                OutputLogger.log("It's not very effective...");
            }

            int value = (int) (e.value * valueK);

            switch (e.type){

                case HEAL:
                    healResult += b.currentFighter()
                            .heal(value);
                    break;

                case DAMAGE:
                    damageResult += b.currentOpponent()
                            .applyDamage(value + b.currentFighter().getPower());
                    break;

                case STATS_DOWN:
                    debuffResult = b.currentOpponent()
                            .lowerStats(value);
                    break;

                case LEECH:
                    int dmg = b.currentOpponent()
                            .applyDamage(value);
                    healResult += b.currentFighter()
                            .heal(dmg);
                    damageResult += dmg;
                    break;

                case STUN:
                    b.currentOpponent()
                            .addStun(value);
                    stunResult+=value;
                    break;

                case PERIODIC:
                    b.currentOpponent()
                            .addPeriodicDamage(value);
                    periodicResult += value;
                    break;

                case STATS_UP:
                    buffResult = b.currentFighter()
                            .increaseStats(value);
                    break;

            }
        }
        return new AttackLog(
                b.currentFighter(),
                new AttackResult(
                        damageResult,
                        debuffResult,
                        stunResult,
                        periodicResult,
                        healResult,
                        buffResult
                ),
                b.currentOpponent(),
                b.getCurrentPlayer()
        );
    }
}
