
package bgs99c.lab2;
import bgs99c.lab2.shared.Types;
import bgs99c.lab2.shared.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Attack extends Move{
public final static int DEFAULT_ACCURACY = 40;
public Attack(Types t){
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

public abstract String description(Fighter f, FighterInfo d);
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
        LEECH(4, true),
        /**
         * Stuns target
         */
        STUN(10, true),
        /**
         * Heals target
         */
        HEAL(6, false),
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

public final Log apply(Battle b){
throw new UnsupportedOperationException();
}
}
