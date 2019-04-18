import bgs99c.lab2.*;
import bgs99c.lab2.shared.Stats;
import bgs99c.lab2.shared.Types;

import java.util.Random;

public class Cheater extends Player {
    public Cheater() {
        super("Cheater", new CheaterStrategy());
    }
}

class Hacker extends Fighter{
    @Override
    public String defendedMessage(FighterInfo attacker) {
        return "u c4n'7 h4rm m3";
    }

    @Override
    public String dyingMessage(FighterInfo killer) {
        return "1'll b3 b4ck";
    }

    @Override
    public String enterFightMessage() {
        return "h4ck1n6 71m3";
    }

    @Override
    public String evasionMessage(FighterInfo attacker) {
        return "c47ch m3 1f u c4n";
    }

    @Override
    public String successfulAttackMessage(FighterInfo target) {
        return "607ch4";
    }

    @Override
    public String unsuccessfulAttackMessage(FighterInfo target) {
        return "1'll 637 u n3x7 71m3";
    }

    @Override
    public String missedMessage(FighterInfo target) {
        return unsuccessfulAttackMessage(target);
    }

    private static int id = 0;
    Hacker() {
        super("Hacker_" + id);
        image = "http://www.mcmbuzz.com/wp-content/uploads/2014/07/025Pikachu_OS_anime_4.png";
        addType(Types.ELECTRIC);
        id++;
        addMove(new Punch());
        addMove(new Hack());
        increaseStat(5, Stats.DEFENCE);
        increaseStat(5, Stats.ATTACK);
        increaseStat(5, Stats.HEALTH);
        increaseStat(5, Stats.SPEED);
    }
}

class Hack extends Attack{
    Hack() {
        super(Types.BUG, false);
        addEffect(new Effect(EffectType.HEAL, 5));
    }

    @Override
    public String description(Fighter f, FighterInfo d) {
        return f + " hacks the game and heals himself";
    }
}

class CheaterStrategy extends Strategy{
    private Random random = new Random();
    @Override
    public Action makeTurn() {
        Hacker hacker = (Hacker) getBattle().currentFighter();
        return hacker.getMoves()[1];//random.nextDouble() > 0.8 ? hacker.getMoves()[1] : hacker.getMoves()[0];
    }
    @Override
    public void levelUp(Fighter f, int points) {
        f.increaseStat(points, Stats.HEALTH);
    }
    @Override
    public Fighter selectFighter(int left) {
        return new Hacker();
    }
    @Override
    public Fighter replaceDead() {
        for(Fighter f : getBattle().allySquad()){
            if(f.getHealth()>0) return f;
        }
        return null;
    }
    @Override
    public Evolution evolve(Fighter[] fs) {
        return null;
    }
}