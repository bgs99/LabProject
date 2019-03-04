package bgs99c.lab2.shared;

import java.io.Serializable;

public class AttackResult implements Serializable{
	static final long serialVersionUID = 42L;
    public final int damage;
    public final int heal;

    public AttackResult(int damage, int heal){
        this.heal = heal;
        this.damage = damage;
    }

}