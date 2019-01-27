package bgs99c.lab2;

/**
 * Strategy which will be used in and after battle for your actions.
 * You should extend this with custom strategy for your player
 */
public abstract class Strategy {
    /**
     * This method should chose next action in a battle
     */
    public abstract Action makeTurn();

    public Battle getBattle(){throw new UnsupportedOperationException();}
    /**
     * This method decides how to spend talent points on level up
     * @param f Fighter which gained new level
     * @param points Points to spend on abilities
     */
    public abstract void levelUp(Fighter f, int points);

    /**
     * This method decides which fighter is going to participate in the tournament/battle in your squad
     * @param left Number of fighters to provide
     * @return Fighter to join your squad
     */
    public abstract Fighter selectFighter(int left);

    /**
     * This method decides which fighter is going to replace the one who got killed
     * @return  Replacement
     */
    public abstract Fighter replaceDead();

    /**
     * This method decides which fighter is going to evolve and into which form
     * @param fighters Your squad
     * @return Chosen evolution. Original fighter must be member of your squad
     */
    public abstract Evolution evolve(Fighter[] fighters);
    /**
     * Class wich represents evolution of a fighter
     */
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
