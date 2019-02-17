
package bgs99c.lab2;

import bgs99c.lab2.shared.AttackResult;
import bgs99c.lab2.shared.LogId;

public final class AttackLog extends Log{
	public static final long serialVersionUID = 42L;
    public AttackLog(FighterInfo u, AttackResult r, FighterInfo s, Player o){
        super(LogType.ATTACK, s, o);
        user = new LogId(u.toString(), u.hashCode());
        result = r;
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
