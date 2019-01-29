package bgs99c.lab2;

import bgs99c.lab2.shared.AttackResult;

public final class DeathLog extends Log {
	static final long serialVersionUID = 42L;

	public DeathLog(FighterInfo s, Player o) {
		super(LogType.DEATH, s, o);
	}
}
