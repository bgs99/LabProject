package bgs99c.lab2;

import bgs99c.lab2.shared.LogId;

public final class ReplacementLog extends Log {
	static final long serialVersionUID = 42L;
    public LogId getReplacement() {
        return replacement;
    }
    private LogId replacement;
}
