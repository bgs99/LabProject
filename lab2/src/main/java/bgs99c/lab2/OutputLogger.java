package bgs99c.lab2;

import java.util.ArrayList;
import java.util.List;

public class OutputLogger {
	private static List<Record> log = new ArrayList<>();
	private static Record cur = new Record();
	public static void markTurn(List<Log> logs) {
		cur.logs.addAll(logs);
		log.add(cur);
		cur = new Record();
	}
	public static void log(String a) {
		cur.msgs.append(a).append('\n');
	}
	public static List<Record> getLogs() {
		if(cur.msgs.length() > 0)
			markTurn(new ArrayList<>());
		return log;
	}
	public static void message(String a, FighterInfo f, Player p){
		if(a == null || a.length() == 0)
			return;
		cur.logs.add(new MessageLog(f, a, p));
	}
	public static void clear() {
		log.clear();
	}
	
}
