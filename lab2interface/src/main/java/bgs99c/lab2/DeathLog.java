
package bgs99c.lab2;

public final class DeathLog extends Log {
	public static final long serialVersionUID = 42L;
	public DeathLog(FighterInfo s, Player o) {
		super(LogType.DEATH, s, o);
	}

}
