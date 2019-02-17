
package bgs99c.lab2;

import bgs99c.lab2.shared.LogId;

public final class ReplacementLog extends Log {
    private LogId replacement;
    public static final long serialVersionUID = 42L;
    ReplacementLog(FighterInfo original, FighterInfo replacement, Player player){
        super(LogType.REPLACEMENT, original, player);
        this.replacement = new LogId(replacement.toString(), replacement.hashCode());
    }

    public LogId getReplacement() {
        return replacement;
    }
}
