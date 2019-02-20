package bgs99c.lab2;

public class MessageLog extends Log {
    public static final long serialVersionUID = 42L;
    public String message;
    public MessageLog(FighterInfo fighterInfo, String message, Player player) {
        super(LogType.MESSAGE, fighterInfo, player);
        this.message = message;
    }
}
