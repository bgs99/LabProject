
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
protected void changeAccuracy(int amount){
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

public abstract String description(Fighter f, FighterInfo d);
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

public Log apply(Battle b){
throw new UnsupportedOperationException();
}
}
