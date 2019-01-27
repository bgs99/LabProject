import bgs99c.lab2.Tournament;
import bgs99c.shared.UserStats;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;

import bgs99c.lab2.OutputLogger;
import bgs99c.lab2.Player;
import bgs99c.lab2.Record;
import bgs99c.lab2.Battle;

public class Game {
	private static Map<String, Player> players = new HashMap<>();
	public Game(UserStats[] users) {
		for(UserStats user : users) {
			players.put(user.name, Loader.loadPlayer(user.name));
		}
	}
	public static synchronized void updatePlayer(String name) {
		players.put(name, Loader.loadPlayer(name));
	}
	public static List<String> getPlayers(){
		return players.values().stream().map(x -> x.getClass().getName()).collect(Collectors.toList());
	}
	public static void clearLogs() {
		OutputLogger.clear();
	}
	public static List<Record> getLogs() {
		return OutputLogger.getLogs();
	}
	public String Battle(String user, String opponent) {
		Battle b = new Battle(players.get(user), players.get(opponent));
		String name = b.start().getClass().getName();
		return name;
	}
	public List<String> Tournament() {
		Tournament t = new Tournament(players.values().toArray(new Player[] {}));
		List<Player> ranks = t.start();
		List<String> names = ranks.stream().map(x -> x.getClass().getName()).collect(Collectors.toList());
		return names;
	}
	public void Test() {
		Player p1 = Loader.loadPlayer("s242322");
		Player p2 = Loader.loadPlayer("Player2");
		Tournament t = new Tournament(new Player[] {p1,p2}) ;
		t.start();
	}
}
