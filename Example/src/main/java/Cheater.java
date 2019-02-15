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
    private static int id = 0;
    Hacker() {
        super("Hacker_" + id);
        image = "http://www.mcmbuzz.com/wp-content/uploads/2014/07/025Pikachu_OS_anime_4.png";
        addType(Types.ELECTRIC);
        id++;
        addMove(new Punch());
        addMove(new Hack());
        increaseStat(5, Stats.DEFENCE);
        increaseStat(5, Stats.POWER);
        increaseStat(5, Stats.HEALTH);
        increaseStat(5, Stats.EVASION);
    }
}

class Hack extends Attack{
    Hack() {
        super(Types.BUG);
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