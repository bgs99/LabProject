
package bgs99c.lab2;

import java.io.Serializable;

import bgs99c.lab2.shared.LogId;

public abstract class Log implements Serializable{
	public static final long serialVersionUID = 42L;
    Log(LogType t, FighterInfo s, Player o){
        type = t;
        subject = new LogId(s.toString(), s.hashCode());
        owner = new LogId(o.getName(), o.hashCode());
    }
    
    private LogType type;
    private LogId subject, owner;

    public LogId getSubject() {
    	return subject;
    }
    public LogId getPlayer() {
    	return owner;
    }

    static Log Fail(FighterInfo s, Player o){
        return new Log(LogType.FAIL, s, o) {static final long serialVersionUID = 42L;};
    }

    public LogType getType() {
        return type;
    }

    public enum LogType{
        ATTACK, REPLACEMENT, DEATH, FAIL, TEAM, MESSAGE, STATUS_EFFECTS,
    }
}
