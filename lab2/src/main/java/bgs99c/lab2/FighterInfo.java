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

    
    abstract StatusEffectsLog applyStatusEffects(Player you, OutputLogger logger);

    boolean paralyzis = false;
    private boolean poison = false;
    private int sleep = 0;
    boolean burn = false;
    private boolean flinch = false;
    private boolean frozen = false;

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

    final void flinch() {
        this.flinch = true;
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

    public final boolean isPoisoned() {
        return poison;
    }

    public final boolean isBurning() {
        return burn;
    }

    public final boolean isAsleep() {
        return  sleep > 0;
    }

    public final boolean isFrozen() {
        return frozen;
    }

    public final boolean isParalyzed() {
        return paralyzis;
    }

    final boolean isStunned() {
        Random r = new Random();
        boolean paralysisFail = r.nextInt(4) == 3;
        boolean result = sleep > 0 || (paralyzis && paralysisFail) || flinch || frozen;
        this.flinch = false;
        this.sleep = sleep == 0 ? 0 : sleep - 1;
        return result;
    }
}
