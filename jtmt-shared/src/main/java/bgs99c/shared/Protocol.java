package bgs99c.shared;

public enum Protocol {
	NONE, SEND_FILE, BATTLE, TOURNAMENT, FEEDBACK, STATUS, STATS, UNKNOWN;

	private static Protocol[] vals = Protocol.values();
	public static Protocol fromInt(int ind) {
		if(ind <=0)
			return UNKNOWN;
		return vals[ind];
	}
}
