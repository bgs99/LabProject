package JavaTournament;

import java.util.List;

import bgs99c.lab2.shared.FighterStats;

public class PlayerModel{
	private String[] columnNames = new String []{"Name", "Rank", "Score", "Avg. rank"};
	private final String name;
    private final int rank, score;
    private final double aRank;
    private final List<FighterStats> fighters;
    public PlayerModel(String name, int rank, int score, double aRank, List<FighterStats> fs) {
        this.name = name;
        this.rank = rank;
        this.score = score;
        this.aRank = aRank;
        this.fighters = fs;
    }
 
    public String getName() {
        return name;
    }

	public int getScore() {
		return score;
	}
	public int getRank() {
		return rank;
	}
	public double getARank() {
		return aRank;
	}
	public List<FighterStats> getFighters(){
		return fighters;
	}
}
