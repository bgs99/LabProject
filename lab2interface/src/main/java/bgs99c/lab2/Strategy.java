
package bgs99c.lab2;


/**
 * Strategy which will be used in and after battle for your actions.
 * You should extend this with custom strategy for your player
 */
public abstract class Strategy {
public Battle getBattle(){
throw new UnsupportedOperationException();
}
public abstract Action makeTurn();
public abstract void levelUp(Fighter f, int points);
public abstract Fighter selectFighter(int left);
public abstract Fighter replaceDead();
public abstract Evolution evolve(Fighter[] fighters);
public class Evolution{
        Fighter from, to;

        /**
         * Creates evolution from given fighter into given form
         * @param a Your fighter
         * @param b New form. Original fighter's class must be immediate superclass of this
         */
        public Evolution(Fighter a, Fighter b){
            from = a;
            to = b;
        }
    }

}
