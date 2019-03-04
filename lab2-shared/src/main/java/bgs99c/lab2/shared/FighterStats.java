package bgs99c.lab2.shared;

import java.io.Serializable;

public class FighterStats implements Serializable{
	private static final long serialVersionUID = 42L;
	public int maxhealth, health, defence, speed, attack, s_attack, s_defence;
	public String img = "", name;
	public Types[] types;

	public FighterStats() {}

	public FighterStats(FighterStats b) {
		maxhealth = b.maxhealth;

		health = b.health;
		defence = b.defence;
		speed = b.speed;
		attack = b.attack;
		s_attack = b.s_attack;
		s_defence = b.s_defence;

		img = b.img;
		name = b.name;
		types = b.types;
	}
}
