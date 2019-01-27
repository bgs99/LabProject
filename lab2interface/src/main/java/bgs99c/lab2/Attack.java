package bgs99c.lab2;
import bgs99c.lab2.shared.*;


public abstract class Attack extends Move{
    public final static int DEFAULT_ACCURACY = 40;
    /**
     * Creates attack with given type
     * @param t Type of attack
     */
    public Attack(Types t){ }

    /**
     * Changes accuracy and subtracts/adds points if amount is positive/negative
     * @param amount Amount the accuracy will change. Positive to increase, negative to decrease
     */
    protected void changeAccuracy(int amount){
    }
    /**
     * Adds effect to attack if there's enough point for that
     * @param e Effect to add
     */
    protected final void addEffect(Effect e){
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
        }
    }

    /**
     * Description which will be displayed when attack is used
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
    public class Result{
        private final int damage;
        private final int stats;
        private final int leech;
        private final int stun;
        private final int periodic;

        Result(int damage, int stats, int leech, int stun, int periodic){

            this.damage = damage;
            this.stats = stats;
            this.leech = leech;
            this.stun = stun;
            this.periodic = periodic;
        }

        public int getDamage() {
            return damage;
        }

        public int getStats() {
            return stats;
        }

        public int getLeech() {
            return leech;
        }

        public int getStun() {
            return stun;
        }

        public int getPeriodic() {
            return periodic;
        }
    }
    public Log apply(Battle b){
        throw new UnsupportedOperationException();
    }
}
