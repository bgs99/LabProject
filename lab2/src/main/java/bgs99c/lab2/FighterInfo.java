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
        types = new ArrayList<Types>();
        Class<?> cc = this.getClass();
        for(;cc.getName() != "bgs99c.lab2.Fighter";evolutionLevel++) {
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
    public Types[] getTypes(){
        return types.toArray(new Types[0]);
    }

    /**
     * Evolution level is the depth of inheritance from Fighter class
     * @return Evolution level
     */
    public int getEvolutionLevel(){
        return evolutionLevel;
    }
    protected List<Types> types;
    protected int typePoints = 0;

    
    abstract Log applyPeriodicDamages(Player you);
    boolean applyStuns() {
        if(stun <= 0) {
            return false;
        }
        stun--;
        return true;
    }
    int getDebuff(){
        return debuff;
    }
    private int stun = 0;
    private int debuff = 0;
    void addStun(int time){
        stun = (stun > time) ? stun : time;
    }
    abstract void addPeriodicDamage(int value);
    public String toString(){
        return this.getClass().getSimpleName() + " " + name;
    }
    /**
     * Returns current health in a battle
     * @return current health
     */
    public abstract int getHealth();
    abstract int applyDamage(int amount);
    void lowerStats(int amount){
        debuff = (debuff > amount) ? debuff : amount;
    };
    abstract int getEvasion();
}
