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

        ret.maxhealth = maxhealth;

        ret.speed = speed;
    	ret.defence = defence;
    	ret.s_defence = s_defence;
    	ret.s_attack = s_attack;
    	ret.attack = attack;
    	ret.health = health;

    	ret.img = image;
    	ret.name = this.toString();
    	ret.types = getTypes();
    	return ret;
    }
    
    /**
     * Fighter's base stats
     */
    public final static int HEALTH = 50, DEFENCE = 10, SPEED = 20,
            ATTACK = 10, SPECIAL_ATTACK = 5, SPECIAL_DEFENCE = 10;
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
    private int maxhealth = HEALTH, health = HEALTH, defence = DEFENCE, speed = SPEED,
            attack = ATTACK, s_attack = SPECIAL_ATTACK, s_defence = SPECIAL_DEFENCE,
            movesLeft = MAXMOVES;

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
        if(movesLeft <= 0){
            throw new OPError();
        }
        movesLeft--;
        moves.add(m);
    }
    @Override
    final StatusEffectsLog applyStatusEffects(Player you, OutputLogger logger){
        int hp_loss = Math.max(health / 16, 1);
        StatusEffectsLog ret = new StatusEffectsLog(this, you);
        ret.stunned = isStunned();
        if(ret.stunned)
            logger.log("Cannot move for this turn");
        if (isBurning()) {
            ret.burn = hp_loss;
            logger.log("Burning for " + hp_loss + "hp");
        }
        if (isPoisoned()) {
            ret.poison = hp_loss;
            logger.log("Getting " + hp_loss + " poison damage");
        }
        this.health -= ret.getDamage();
        return ret;
    }
    @Override
    final int applyDamage(int amount, OutputLogger logger){
        if(amount <= 0){
            logger.log("This attack is too weak");
            return 0;
        }
        logger.log("It did " + amount + " damage");
        health -= amount;
        return amount;
    }
    final int heal(int amount, OutputLogger logger){
        int previousHealth = health;
        health += amount * previousHealth / 100;
        health = health > maxhealth ? maxhealth : health;
        logger.log(this + "is healed for " + (health - previousHealth) + " HP.");
        return health - previousHealth;
    }

    /**
     * Returns accuracy considering debuff
     */
    public final int getAccuracy(){
        return stages.get(BattleStats.ACCURACY);
    }

    /**
     * Returns evasion considering debuff
     */
    @Override
    public final int getEvasion() {
        return stages.get(BattleStats.EVASION);
    }
    public final int getDefence() {
        return getWithDebuff(BattleStats.DEFENCE, defence);
    }

    private int getWithDebuff(BattleStats s, int val) {
        double stage = stages.get(s);
        double buff = stage >= 0 ? (2 + stage) / 2 : 2 / (2 + stage);
        return (int)(val * buff);
    }

    public final int getAttack() {
        return getWithDebuff(BattleStats.ATTACK, attack) / (burn ? 2 : 1);
    }

    public final int getSAttack() {
        return getWithDebuff(BattleStats.SPECIAL_ATTACK, s_attack);
    }

    @Override
    public final int getSDefence() {
        return getWithDebuff(BattleStats.SPECIAL_DEFENCE, s_defence);
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
            case HEALTH:
                maxhealth   += amount;
                health      += amount;
                break;
            case DEFENCE:
                defence     += amount;
                break;
            case SPEED:
                speed     += amount;
                break;
            case ATTACK:
                attack    += amount;
                break;
            case SPECIAL_DEFENCE:
                s_defence += amount;
                break;
            case SPECIAL_ATTACK:
                s_attack += amount;
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

    @Override
    final int getSpeed() {
        return getWithDebuff(BattleStats.SPEED, speed) / (paralyzis ? 4 : 1);
    }
}
