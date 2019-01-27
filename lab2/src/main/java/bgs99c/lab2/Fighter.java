package bgs99c.lab2;
import bgs99c.lab2.shared.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Fighter extends FighterInfo{
    private int talentPoints = 20;
    final static int LVLPOINTS = 5;
    private final static int MAXMOVES = 5;
    
    FighterStats getStats() {
    	FighterStats ret = new FighterStats();
    	ret.accuracy = accuracy;
    	ret.defence = defence;
    	ret.evasion = evasion;
    	ret.maxhealth = maxhealth;
    	ret.power = power;
    	ret.health = health;
    	ret.img = image;
    	ret.name = this.toString();
    	System.out.println(ret.img);
    	return ret;
    }
    
    /**
     * Fighter's stats
     */
    public final static int HEALTH = 50, DEFENCE = 10, ACCURACY = 20, EVASION = 10, POWER = 5;
    void reset(){
        health = maxhealth;
    }
    /**
     * Adds new type to fighter if it's evolution level is enough
     * @param t New type
     */
    public void addType(Types t){
        if(typePoints <= 0){
            OutputLogger.log("Too many types for " + this);
            return;
        }
        types.add(t);
        typePoints--;
    }
    /**
     * Fighter's avatar
     */
    public String image = "";
    private int maxhealth = HEALTH, health = HEALTH, defence = DEFENCE, accuracy = ACCURACY,
            evasion = EVASION, power = POWER, movesLeft = MAXMOVES;
    private List<Integer> periodicDamages = new ArrayList<>();
    private List<Move> moves = new ArrayList<>();

    /**
     * Returns moves that fighter could use
     */
    public Move[] getMoves(){
        return moves.toArray(new Move[0]);
    }

    /**
     * Adds moves if there's not too many already (max = 5)
     * @param m New move
     */
    public void addMove(Move m){
        if(movesLeft <=0){
            OutputLogger.log(this.getClass().getSimpleName() + " cannot learn so many moves.");
            return;
        }
        movesLeft--;
        moves.add(m);
    }
    Log applyPeriodicDamages(Player you){
        List<Integer> pd = periodicDamages;
        int sum = 0;
        for(int i = 0; i < pd.size();i++){
            int damage = pd.get(i);
            OutputLogger.log(this + " is losing " + damage + " hp because of periodic damage.");
            health -= damage;
            sum+=damage;
            pd.set(i, damage-1);
            if(damage <= 0){
                pd.remove(i);
                i--;
            }
        }
        return new PeriodicDamageLog(this, you, sum);
    }
    void addPeriodicDamage(int value){
        if(value<0){
            return;
        }
        OutputLogger.log("It's " + value + " points of periodic damage!");
        periodicDamages.add(value);
    }
    int applyDamage(int amount){
        if(amount <= defence){
            OutputLogger.log("This attack is too weak");
            return 0;
        }
        OutputLogger.log("It did " + (amount-defence) + " damage");
        health -= amount - defence;
        return amount-defence;
    }
    void heal(int amount){
        int sh = health;
        health += amount;
        health = health > maxhealth ? maxhealth : health;
        OutputLogger.log(this + "is healed for " + (health - sh) + " hp.");
    }

    /**
     * Returns accuracy considering debuff
     */
    public int getAccuracy(){
        return accuracy - getDebuff();
    }

    /**
     * Returns evasion considering debuff
     */
    public int getEvasion() {
        return evasion - getDebuff();
    }
    public int getDefence() {
    	return defence - getDebuff();
    }

    /**
     * Returns power considering debuff
     */
    public int getPower() {
        return power - getDebuff();
    }

    /**
     * Creates fighter with given name
     * @param n Name
     */
    public Fighter(String n){
        super(n);
    }

    /**
     * Changes fighter's stats and substracts/adds ability points if you increase/decrease them
     * @param amount Amount the ability will be changed
     * @param stat Stat that will change
     */
    public void changeAbility(int amount, Stats stat){
        if(talentPoints < amount){
            OutputLogger.log(this.getClass().getSimpleName() + " is trying to use steroids. With no luck.");
            return;
        }
        talentPoints -= amount;
        switch (stat){
            case POWER:
                power+=amount;
                break;
            case HEALTH:
                maxhealth+=amount;
                health+=amount;
                break;
            case DEFENCE:
                defence += amount;
                break;
            case EVASION:
                evasion += amount;
                break;
            case ACCURACY:
                accuracy += amount;
                break;
        }
    }

    /**
     * Returns current health considering damage in battle
     */
    public int getHealth(){
        return health;
    }
    void addTalentPoints(){
        talentPoints += LVLPOINTS;
    }
}