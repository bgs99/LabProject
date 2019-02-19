package bgs99c.lab2;
import java.util.Arrays;

/**
 * Action of switching current fighter. Takes whole turn
 */
public final class ReplaceFighter implements Action{
    private Fighter fighter;

    /**
     * Creates Action that switches current fighter with given.
     * @param replace Replacement. Must be member of your squad.
     */
    public ReplaceFighter(Fighter replace){
        fighter = replace;
    }
    public Log apply(Battle b){
        if (!Arrays.asList(b.allySquad()).contains(fighter)) {
            OutputLogger.log(b.currentFighter() + " is asking for replacement, but " + fighter + " is not around");
            return Log.Fail(b.currentFighter(), b.getCurrentPlayer());
        }
        Fighter original = b.currentFighter();
        OutputLogger.log(original + " is replaced by " + fighter);
        b.changeFighter(fighter);
        return new ReplacementLog(original, fighter, b.getCurrentPlayer());
    }
}
