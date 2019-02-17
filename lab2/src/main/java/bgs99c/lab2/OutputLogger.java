package bgs99c.lab2;

import java.util.ArrayList;
import java.util.List;

public class OutputLogger {
	private List<Record> log = new ArrayList<>();
	private Record cur = new Record();
	void markTurn(List<Log> logs) {
		cur.logs.addAll(logs);
		log.add(cur);
		cur = new Record();
	}
	void log(String a) {
		cur.msgs.append(a).append('\n');
	}
	public List<Record> getLogs() {
		if(cur.msgs.length() > 0)
			markTurn(new ArrayList<>());
		return log;
	}
	void message(String a, FighterInfo f, Player p){
		if(a == null || a.length() == 0)
			return;
		cur.logs.add(new MessageLog(f, a, p));
	}
}
