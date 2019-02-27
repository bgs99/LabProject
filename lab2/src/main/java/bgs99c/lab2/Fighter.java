package bgs99c.lab2;
import bgs99c.lab2.shared.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Fighter extends FighterInfo{
    private int talentPoints = 20;
    final static int LVLPOINTS = 5;
    private final static int MAXMOVES = 5;
    
    public final FighterStats getStats() {
    	FighterStats ret = new FighterStats();
    	ret.accuracy = accuracy;
    	ret.defence = defence;
    	ret.evasion = evasion;
    	ret.maxhealth = maxhealth;
    	ret.power = power;
    	ret.health = health;
    	ret.img = image;
    	ret.name = this.toString();
    	return ret;
    }
    
    /**
     * Fighter's base stats
     */
    public final static int HEALTH = 50, DEFENCE = 10, ACCURACY = 20, EVASION = 10, POWER = 5;
    final void reset(){
        health = maxhealth;
    }
    /**
     * Adds new type to fighter if it's evolution level is enough
     * @param t New type
     */
    public final void addType(Types t){
        if(typePoints <= 0){
            throw new OPError();
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
    public final Move[] getMoves(){
        return moves.toArray(new Move[0]);
    }

    /**
     * Adds moves if there's not too many already (max = 5)
     * @param m New move
     */
    public final void addMove(Move m){
        if(movesLeft <=0){
            throw new OPError();
        }
        movesLeft--;
        moves.add(m);
    }
    @Override
    final Log applyPeriodicDamages(Player you, OutputLogger logger){
        int sum = 0;
        for(int i = 0; i < periodicDamages.size(); i++){
            int damage = periodicDamages.get(i);
            logger.log(this + " is losing " + damage + " HP because of periodic damage.");
            health -= damage;
            sum += damage;
            periodicDamages.set(i, damage-1);
            if(damage <= 0){
                periodicDamages.remove(i);
                i--;
            }
        }
        return new PeriodicDamageLog(this, you, sum);
    }
    @Override
    final void addPeriodicDamage(int value, OutputLogger logger){
        if(value < 0){
            return;
        }
        logger.log("It's " + value + " points of periodic damage!");
        periodicDamages.add(value);
    }
    @Override
    final int applyDamage(int amount, OutputLogger logger){
        if(amount <= defence){
            logger.log("This attack is too weak");
            return 0;
        }
        logger.log("It did " + (amount-defence) + " damage");
        health -= amount - defence;
        return amount - defence;
    }
    final int heal(int amount, OutputLogger logger){
        int previousHealth = health;
        health += amount;
        health = health > maxhealth ? maxhealth : health;
        logger.log(this + "is healed for " + (health - previousHealth) + " HP.");
        return health - previousHealth;
    }

    /**
     * Returns accuracy considering debuff
     */
    public final int getAccuracy(){
        return accuracy - getDebuff();
    }

    /**
     * Returns evasion considering debuff
     */
    @Override
    public final int getEvasion() {
        return evasion - getDebuff();
    }
    public final int getDefence() {
    	return defence - getDebuff();
    }

    /**
     * Returns power considering debuff
     */
    public final int getPower() {
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
    public final void increaseStat(int amount, Stats stat){
        if(talentPoints < amount){
            throw new OPError();
        }
        talentPoints -= amount;
        switch (stat){
            case POWER:
                power       += amount;
                break;
            case HEALTH:
                maxhealth   += amount;
                health      += amount;
                break;
            case DEFENCE:
                defence     += amount;
                break;
            case EVASION:
                evasion     += amount;
                break;
            case ACCURACY:
                accuracy    += amount;
                break;
        }
    }
    public  String missedMessage(FighterInfo target) {
        return null;
    }
    public String dyingMessage(FighterInfo killer) {
        return null;
    }
    public String enterFightMessage() {
        return null;
    }
    public String evasionMessage(FighterInfo attacker){
        return null;
    }
    public String defendedMessage(FighterInfo attacker){
        return null;
    }
    public String successfulAttackMessage(FighterInfo target){
        return null;
    }
    public String unsuccessfulAttackMessage(FighterInfo target) {
        return null;
    }
    /**
     * Returns current health considering damage in battle
     */
    public final int getHealth(){
        return health;
    }
    @Override
    public final double getHealthBar() {
        return health * 1.0 / maxhealth;
    }
    final void addTalentPoints(){
        talentPoints += LVLPOINTS;
    }
}
