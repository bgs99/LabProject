package bgs99c.lab2;

import bgs99c.lab2.shared.AttackResult;
import bgs99c.lab2.shared.LogId;

public final class AttackLog extends Log{
	static final long serialVersionUID = 42L;
    public AttackLog(FighterInfo u, AttackResult r, FighterInfo s, Player o){
        super(null, s, o);
    }
    public FighterInfo getUser(Battle b) {
        return null;
    }
    private LogId user;
    private AttackResult result;

    public LogId getUser() {
        return user;
    }
    public AttackResult getResult() {
        return result;
    }
}
