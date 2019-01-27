package bgs99c.shared;

public enum Protocol {
	NONE, SEND_FILE, BATTLE, TOURNAMENT, FEEDBACK, STATUS, STATS;
	private static Protocol[] vals = Protocol.values();
	public static Protocol fromInt(int ind) {
		if(ind <=0)
			return NONE;
		return vals[ind];
	}
	public byte toByte() {
		return (byte)this.ordinal();
	}
}
