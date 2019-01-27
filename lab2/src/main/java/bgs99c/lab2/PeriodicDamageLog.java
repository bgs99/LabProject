package bgs99c.lab2;

public final class PeriodicDamageLog extends Log {
	static final long serialVersionUID = 42L;
	private int damage;
	PeriodicDamageLog(FighterInfo s, Player o, int d) {
		super(LogType.LASTING, s, o);
		damage = d;
	}
	public int getDamage() {
		return damage;
	}
}
