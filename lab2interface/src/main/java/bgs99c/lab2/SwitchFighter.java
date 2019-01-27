package bgs99c.lab2;


/**
 * Action of switching current fighter. Takes whole turn
 */
public final class SwitchFighter implements Action{
    /**
     * Creates Action that switches current fighter with given.
     * @param replace Replacement. Must be member of your squad.
     */
    public SwitchFighter(Fighter replace){
    }
    public Log apply(Battle b){
        throw new UnsupportedOperationException();
    }
}
