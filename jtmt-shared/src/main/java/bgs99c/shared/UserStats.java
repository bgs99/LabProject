package bgs99c.shared;

import java.io.Serializable;
import java.util.List;


public class UserStats implements Serializable {
	public String name;
	public int lastpos;
	public double avgpos;
	public int score;
	public List<FighterStats> fighters;

	public UserStats(String name, int lastpos, double avgpos, int score, List<FighterStats> fighters) {
		this.name = name;
		this.lastpos = lastpos;
		this.avgpos = avgpos;
		this.score = score;
		this.fighters = fighters;
	}
}
