package bgs99c.lab2.shared;

import java.io.Serializable;

public class AttackResult implements Serializable{
	static final long serialVersionUID = 42L;
    private final int damage;
    private final int stats;
    private final int leech;
    private final int stun;
    private final int periodic;

    public AttackResult(int damage, int stats, int leech, int stun, int periodic){

        this.damage = damage;
        this.stats = stats;
        this.leech = leech;
        this.stun = stun;
        this.periodic = periodic;
    }

    public int getDamage() {
        return damage;
    }

    public int getStats() {
        return stats;
    }

    public int getLeech() {
        return leech;
    }

    public int getStun() {
        return stun;
    }

    public int getPeriodic() {
        return periodic;
    }
}