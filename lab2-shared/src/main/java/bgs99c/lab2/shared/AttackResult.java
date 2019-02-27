package bgs99c.lab2.shared;

import java.io.Serializable;

public class AttackResult implements Serializable{
	static final long serialVersionUID = 42L;
    public final int damage;
    public final int debuff;
    public final int buff;
    public final int heal;
    public final int stun;
    public final int periodic;

    public AttackResult(int damage, int debuff, int stun, int periodic, int heal, int buff){
        this.heal = heal;
        this.damage = damage;
        this.debuff = debuff;
        this.stun = stun;
        this.periodic = periodic;
        this.buff = buff;
    }

}