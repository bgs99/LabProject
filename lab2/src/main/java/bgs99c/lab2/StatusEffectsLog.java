package bgs99c.lab2;

public class StatusEffectsLog extends Log {
    boolean stunned = false;
    int poison = 0;
    int burn = 0;
    public int getPoison() {
        return poison;
    }

    public int getBurn() {
        return burn;
    }

    public int getDamage() {
        return burn + poison;
    }
    StatusEffectsLog(FighterInfo s, Player o) {
        super(LogType.STATUS_EFFECTS, s, o);
    }
}
