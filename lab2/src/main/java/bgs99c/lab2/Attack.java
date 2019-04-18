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
    private boolean physical;
    /**
     * Creates attack with given type
     * @param t Type of attack
     */
    public Attack(Types t, boolean physical){
        type = t;
        this.physical = physical;
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
         * @param s Affected stat
         */
        public Effect(EffectType t, int v, BattleStats s) {
            this(t, v);
            this.stat = s;
        }
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
        BattleStats stat;
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
        DAMAGE(1),
        /**
         * Periodic damage, until the end of battle
         */
        POISON(1),
        /**
         * Redused speed and chance of skipping
         */
        PARALYSIS(1),
        /**
         * Periodic damage, reduced attack
         */
        BURN(1),
        /**
         * Heals target
         */
        HEAL(1),
        /**
         * Cannot use moves
         */
        SLEEP(12),
        /**
         * Adds buff to your own stat
         */
        STAT_UP(1),
        /**
         * Adds debuff to opponent's stat
         */
        STAT_DOWN(1),
        /**
         * Heals to some percent of the damage
         */
        LEECH(1),
        /**
         *Freezes opponent
         */
        FREEZE(10);
        int price;

        EffectType(int price) {
            this.price = price;
        }
    }
    private boolean rollDice(Fighter attack, FighterInfo defence, OutputLogger logger){
        Random r = new Random();
        int v = r.nextInt(100);
        double stage = attack.getAccuracy() - defence.getEvasion();
        stage = stage > 6 ? 6 : stage;
        stage = stage < -6 ? -6 : stage;
        double buff = stage >= 0 ? (3 + stage) / 3 : 3 / (3 - stage);
        if (v > accuracy * buff){
            logger.log(defence + " evaded " + attack + "'s attack");
            return false;
        }
        return true;
    }
    public final Log apply(Battle b){
        if(! Arrays.asList(b.currentFighter().getMoves()).contains(this)) {
            b.logger.log(b.currentFighter() + " doesn't know how to use " + this);
            return Log.Fail(b.currentFighter(),b.getCurrentPlayer());
        }

        b.logger.log(description(b.currentFighter(), b.currentOpponent()));

        if(!rollDice(b.currentFighter(), b.currentOpponent(), b.logger)){
            try {
                String evasionMessage = ((Fighter) b.currentOpponent()).evasionMessage(b.currentFighter());
                b.logger.message(evasionMessage, b.currentOpponent(), b.getCurrentPlayer());
            } catch (Exception ignored){

            }
            b.logger.message(
                    b.currentFighter().missedMessage(b.currentOpponent()),
                    b.currentFighter(),
                    b.getCurrentPlayer()
            );
            return Log.Fail(b.currentFighter(), b.getCurrentPlayer());
        }

        boolean offence = false;

        int damageResult    = 0;
        int healResult      = 0;

        for(Effect e : effects){

            double valueK = 1;

            FighterInfo target = e.type != EffectType.HEAL ? b.currentOpponent() : b.currentFighter();

            for(Types t : target.getTypes()) {
                valueK *= checkEffect(type, t);
                if (t == Types.FIRE) {
                    target.unfreeze();
                }
            }

            if(valueK >=2 ) {
                b.logger.log("It's super effective!");
            } else if(valueK <= 0.5) {
                b.logger.log("It's not very effective...");
            }

            int value = (int) (e.value * valueK);

            switch (e.type){

                case HEAL:
                    healResult += b.currentFighter()
                            .heal(value, b.logger);
                    break;

                case DAMAGE:
                    offence = true;
                    int A = physical ? b.currentFighter().getAttack() : b.currentFighter().getSAttack();
                    int D = physical ? b.currentFighter().getDefence() : b.currentFighter().getSDefence();

                    int damage = 2 * A * value / D / 50 + 2;

                    damageResult += b.currentOpponent()
                            .applyDamage(damage, b.logger);
                    break;

                case STAT_DOWN:
                    offence = true;
                    b.currentOpponent()
                            .lowerStat(e.stat);
                    break;

                case LEECH:
                    offence = true;
                    int dmg = b.currentOpponent()
                            .applyDamage(value, b.logger);
                    healResult += b.currentFighter()
                            .heal(dmg, b.logger);
                    damageResult += dmg;
                    break;

                case POISON:
                    offence = true;
                    b.currentOpponent()
                            .poison();
                    break;

                case BURN:
                    offence = true;
                    b.currentOpponent().burn();
                    break;

                case STAT_UP:
                    b.currentFighter()
                            .increaseStat(e.stat);
                    break;

                case SLEEP:
                    b.currentOpponent().sleep();
                    break;

                case PARALYSIS:
                    b.currentOpponent().paralyze();
                    break;
                case FREEZE:
                    b.currentOpponent().freeze();
                    break;
            }
        }
        if(damageResult == 0 && offence){
            try {
                String ignoredMessage = ((Fighter) b.currentOpponent()).defendedMessage(b.currentFighter());
                b.logger.message(ignoredMessage, b.currentOpponent(), b.getCurrentPlayer());
            } catch (Exception ignored){

            }
            b.logger.message(
                    b.currentFighter().unsuccessfulAttackMessage(b.currentOpponent()),
                    b.currentFighter(),
                    b.getCurrentPlayer()
            );
        } else if(offence) {
            b.logger.message(
                    b.currentFighter().successfulAttackMessage(b.currentOpponent()),
                    b.currentFighter(),
                    b.getCurrentPlayer()
            );
        }
        return new AttackLog(
                b.currentFighter(),
                new AttackResult(
                        damageResult,
                        healResult
                ),
                b.currentOpponent(),
                b.getCurrentPlayer()
        );
    }
}
