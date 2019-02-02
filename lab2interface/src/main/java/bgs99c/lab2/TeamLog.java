
package bgs99c.lab2;

import bgs99c.lab2.shared.FighterStats;

public final class TeamLog extends Log {
	TeamLog(Fighter s, Player o) {
		super(LogType.TEAM, s, o);
		fs = s.getStats();
		// TODO Auto-generated constructor stub
	}
	public FighterStats fs;
	public static final long serialVersionUID = 42L;

}
