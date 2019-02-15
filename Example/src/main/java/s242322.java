import bgs99c.lab2.shared.*;
import bgs99c.lab2.*;

class SimpleStrategy extends Strategy{

    private FighterInfo lastOpponent = null;
    private double lastHealth = Double.POSITIVE_INFINITY;

    @Override
    public void observeResults(){
        Battle battle = getBattle();
        FighterInfo opponent = battle.currentOpponent();

        lastOpponent = opponent;
        lastHealth = opponent.getHealthBar();
    }

    @Override
    public Action makeTurn() throws Exception {
        Battle battle = getBattle();
        FighterInfo opponent = battle.currentOpponent();

        boolean cheater = opponent.getClass().getSimpleName().equals("Hacker");

        if(cheater && opponent == lastOpponent && opponent.getHealthBar() > lastHealth) {
            System.out.println("Got the cheater!");
            throw new Exception("Cheater!");
        }

        return battle.currentFighter().getMoves()[0];
    }
    @Override
    public void levelUp(Fighter f, int points) {
        f.increaseStat(points, Stats.HEALTH);
    }
    @Override
    public Fighter selectFighter(int left) {
        return new Soldier();
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
        for (Fighter f : fs) {
            if (f instanceof Soldier)
                return new Evolution(f, new SuperSoldier());
        }
        return null;
    }
}
public class s242322 extends Player{
    public s242322(){
        super("BR034241436", new SimpleStrategy());
    }
}
class Player3 extends Player{
    Player3(){
        super("Bro", new SimpleStrategy());
    }
}


/*public class Player1 extends Player{
    public Player1(){
        super("Bgs", new SimpleStrategy());
    }
}*/
class Player2 extends Player{
    Player2(){
        super("Sgb", new SimpleStrategy());
    }
}

class SuperSoldier extends Soldier{
    SuperSoldier(){
        super();
        addType(Types.FIRE);
    }
}

class Soldier extends Fighter{
    private static int id = 0;
    Soldier() {
        super("Soldat" + id);
        image = "http://www.mcmbuzz.com/wp-content/uploads/2014/07/025Pikachu_OS_anime_4.png";
        addType(Types.NORMAL);
        id++;
        addMove(new Punch());
        increaseStat(5, Stats.DEFENCE);
        increaseStat(5, Stats.POWER);
        increaseStat(5, Stats.HEALTH);
        increaseStat(5, Stats.EVASION);
    }
}
class Punch extends Attack {
    Punch(){
        super(Types.FIGHTING);
        addEffect(new Effect(EffectType.DAMAGE, Move.ABLPOINTS));
    }
    @Override
    public String description(Fighter f, FighterInfo t){
        return f + " punched " + t +" in the face";
    }
}
