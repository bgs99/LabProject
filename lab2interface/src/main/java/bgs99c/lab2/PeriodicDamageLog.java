package bgs99c.lab2;

import bgs99c.lab2.shared.AttackResult;
import bgs99c.lab2.shared.LogId;

public final class PeriodicDamageLog extends Log {
    PeriodicDamageLog(FighterInfo s, Player o, int d) {
        super(LogType.LASTING, s, o);
    }
	static final long serialVersionUID = 42L;
	private int damage;
	public int getDamage() {
		return damage;
	}
}
