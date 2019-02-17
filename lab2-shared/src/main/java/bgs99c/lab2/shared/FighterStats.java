package bgs99c.lab2.shared;

import java.io.Serializable;

public class FighterStats implements Serializable{
	private static final long serialVersionUID = 42L;
	public int maxhealth, health, defence, accuracy, evasion, power;
	public String img = "", name;
	@Override
	public String toString() {
		return "health\t" + health + "/" + maxhealth + "\nagility\t" + defence +
				"\naccuracy\t" + accuracy + "\nevasion\t" + evasion + "\npower\t" + power;
	}
	public FighterStats() {}
	public FighterStats(FighterStats b) {
		maxhealth = b.maxhealth;
		health = b.health;
		defence = b.defence;
		accuracy = b.accuracy;
		evasion = b.evasion;
		power = b.power;
		img = b.img;
		name = b.name;
	}
}
