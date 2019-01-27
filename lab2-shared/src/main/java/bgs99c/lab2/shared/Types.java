package bgs99c.lab2.shared;



/**
 * Types of fighters
 */
public enum Types {
    /**
     * Good against cavalry, weak against ranged
     */
    MELEE(0),
    /**
     * Good against melee, weak against cavalry
     */
    RANGED(1),
    /**
     * Good against ranged, weak against melee
     */
    CAVALRY(2);
    private final int good;
    private Types(int v){
        good = v;
    }
    public boolean goodAgainst(Types b){
        switch (good){
            case 0:
                return b == Types.CAVALRY;
            case 1:
                return b == Types.MELEE;
            case 2:
                return b == Types.RANGED;
        }
        return false;
    }
    public boolean badAgainst(Types b){
        return b.goodAgainst(this);
    }
}
