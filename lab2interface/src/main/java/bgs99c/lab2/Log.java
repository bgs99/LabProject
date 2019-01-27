package bgs99c.lab2;

import java.io.Serializable;

import bgs99c.lab2.Log.LogType;
import bgs99c.lab2.shared.LogId;

public abstract class Log implements Serializable{
	static final long serialVersionUID = 42L;
	Log(){}
	private LogId subject, owner;
	public LogId getSubject() {
    	return subject;
    }
    public LogId getPlayer() {
    	return owner;
    }
    public LogType getType() {
        return type;
    }

    private LogType type;
    public enum LogType{
        ATTACK, REPLACEMENT, DEATH, STUN, LASTING, SUPPORT, FAIL, TEAM
    }
}
