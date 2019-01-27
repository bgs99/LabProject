package bgs99c.lab2.shared;

import java.io.Serializable;

public class LogId implements Serializable{
	private static final long serialVersionUID = 42L;
	public String name;
	public int hash;
	public LogId(String name, int hash) {
		this.name = name;
		this.hash = hash;
	}
	@Override
	public int hashCode() {
		return name.hashCode() + hash;
	}
	@Override
	public boolean equals(Object b) {
		if(b == null || b.getClass().getName() != LogId.class.getName())
			return false;
		LogId bl = (LogId)b;
		return bl.name.equals(name) && bl.hash == hash;
	}
}
