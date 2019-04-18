
package bgs99c.lab2;
import bgs99c.lab2.shared.Types;
import bgs99c.lab2.shared.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Attack extends Move{
public final static int DEFAULT_ACCURACY = 40;
public Attack(Types t, boolean physical){
throw new UnsupportedOperationException();
}
protected final void changeAccuracy(int amount){
throw new UnsupportedOperationException();
}
protected final void addEffect(Effect e){
throw new UnsupportedOperationException();
}
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

public abstract String description(Fighter f, FighterInfo d);
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

public final Log apply(Battle b){
throw new UnsupportedOperationException();
}
}
