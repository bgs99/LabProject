package bgs99c.shared;

import java.io.Serializable;
import java.util.List;

import bgs99c.lab2.shared.Types;

public class FighterStats implements Serializable {
	public String name;
	public int health, defence, accuracy, evasion, power;
	public String parent = "";
	public String image;
	public Types[] types;
}
