package bgs99c.lab2;


/**
 * Strategy which will be used in and after battle for your actions.
 * You should extend this with custom strategy for your player
 */
public abstract class Strategy {
    void init(Battle b){
        battle = b;
    }
    private Battle battle;

    public void observeResults(){}

    public Battle getBattle(){
        return battle;
    }
    /**
     * This method should chose next action in a battle
     */
    public abstract Action makeTurn() throws Exception;

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
    Fighter[] applyEvolve(Player p){
        Fighter[] b = p.fighters;
        Evolution r = evolve(b);
        if(r == null)
            return b;
        if(r.to.getClass().getSuperclass().getName() == r.from.getClass().getName()) {
            for(int i = 0; i< b.length;i++){
                if(b[i] == r.from) {
                    b[i] = r.to;
                    OutputLogger.log(r.from + " evolves into " + r.to);
                    return b;
                }
            }
        }
        OutputLogger.log("Winner cannot chose right evolution and loses this opportunity.");
        return b;
    }
    Fighter selectFighterTournament(int left){
        Fighter r = selectFighter(left);
        if(r.getEvolutionLevel()>1){
            OutputLogger.log(battle.currentFighter() + " is cheating. Usage of evolved fighters is prohibited. " +
                                battle.currentFighter() + " is disqualified");
            return null;
        }
        return r;
    }
    void applyLevelUp(Fighter f){
        OutputLogger.log(f + " gets new level!");
        f.addTalentPoints();
        levelUp(f, Fighter.LVLPOINTS);
    }

    /**
     * Class that represents evolution of a fighter
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
