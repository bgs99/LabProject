package bgs99c.lab2;
import bgs99c.lab2.shared.*;

import java.util.*;

public abstract class FighterInfo {
	@Override
	public final int hashCode() {
		return super.hashCode();
	}
    FighterInfo(String n){
        for (BattleStats s : BattleStats.values()) {
            stages.put(s, 0);
        }
        name = n;
        types = new ArrayList<>();
        Class<?> cc = this.getClass();
        for(; !cc.getName().equals("bgs99c.lab2.Fighter"); evolutionLevel++) {
            cc = cc.getSuperclass();
        }
        typePoints = evolutionLevel;
    }

    private int evolutionLevel;
    private String name;

    /**
     * Returns this fighter's types.
     * @return Returns this fighter's types. Can be empty.
     */
    public final Types[] getTypes(){
        return types.toArray(new Types[0]);
    }

    /**
     * Evolution level is the depth of inheritance from Fighter class
     * @return Evolution level
     */
    public final int getEvolutionLevel(){
        return evolutionLevel;
    }
    protected List<Types> types;
    protected int typePoints = 0;

    
    abstract Log applyPeriodicDamages(Player you, OutputLogger logger);
    final boolean applyStuns() {
        if(stun <= 0) {
            return false;
        }
        stun--;
        return true;
    }
    private int stun = 0;
    boolean paralyzis = false;
    private boolean poison = false;
    private int sleep = 0;
    boolean burn = false;
    private boolean flinch = false;
    private boolean frozen = false;
    final void addStun(int time){
        stun = (stun > time) ? stun : time;
    }
    abstract void addPeriodicDamage(int value, OutputLogger logger);
    public final String toString(){
        return this.getClass().getSimpleName() + " " + name;
    }

    abstract int getSDefence();

    /**
     * Returns current health percent in a battle
     * @return current health
     */
    public abstract double getHealthBar();
    abstract int applyDamage(int amount, OutputLogger logger);
    abstract int getEvasion();

    Map<BattleStats, Integer> stages = new HashMap<>();


    final int increaseStat(BattleStats s) {
        int stage = stages.get(s);
        if (stage >= 6)
            return 0;
        stages.replace(s, stage + 1);
        return 1;
    }
    final int lowerStat(BattleStats s){
        int stage = stages.get(s);
        if (stage <= -6)
            return 0;
        stages.replace(s, stage - 1);
        return -1;
    }

    final void poison() {
        this.poison = true;
    }

    final void sleep() {
        Random r = new Random();
        this.sleep = r.nextInt(7) + 1;
    }

    final void burn() {
        this.burn = true;
    }

    final void paralyze() {
        this.paralyzis = true;
    }

    abstract int getSpeed();

    final void freeze() {
        this.frozen = true;
    }

    final void unfreeze() {
        this.frozen = false;
    }
}
