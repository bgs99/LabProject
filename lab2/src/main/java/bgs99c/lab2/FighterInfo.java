package bgs99c.lab2;
import bgs99c.lab2.shared.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FighterInfo {
	@Override
	public final int hashCode() {
		return super.hashCode();
	}
    FighterInfo(String n){
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
    final int getDebuff(){
        return debuff - buff;
    }
    private int stun = 0;
    private int debuff = 0;
    private int buff = 0;
    final void addStun(int time){
        stun = (stun > time) ? stun : time;
    }
    abstract void addPeriodicDamage(int value, OutputLogger logger);
    public final String toString(){
        return this.getClass().getSimpleName() + " " + name;
    }
    /**
     * Returns current health percent in a battle
     * @return current health
     */
    public abstract double getHealthBar();
    abstract int applyDamage(int amount, OutputLogger logger);
    abstract int getEvasion();

    final int increaseStats(int amount) {
        buff = (buff > amount) ? buff : amount;
        return buff;
    }
    final int lowerStats(int amount){
        debuff = (debuff > amount) ? debuff : amount;
        return debuff;
    }
}
