package bgs99c.lab2;

public final class DeathLog extends Log {
	static final long serialVersionUID = 42L;
	DeathLog(FighterInfo s, Player o) {
		super(LogType.DEATH, s, o);
	}

}
