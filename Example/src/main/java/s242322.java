import bgs99c.lab2.shared.*;
import bgs99c.lab2.*;

class SimpleStrategy extends Strategy{
    public Action makeTurn() {
        return getBattle().currentFighter().getMoves()[0];
    }
    public void levelUp(Fighter f, int points) {
        f.increaseStat(points, Stats.HEALTH);
        
    }
    public Fighter selectFighter(int left) {
        return new Soldier();
    }
    public Fighter replaceDead() {
        for(Fighter f : getBattle().allySquad()){
            if(f.getHealth()>0) return f;
        }
        return null;
    }
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
    public Player3(){
        super("Bro", new SimpleStrategy());
    }
}


/*public class Player1 extends Player{
    public Player1(){
        super("Bgs", new SimpleStrategy());
    }
}*/
class Player2 extends Player{
    public Player2(){
        super("Sgb", new SimpleStrategy());
    }
}

class SuperSoldier extends Soldier{
    public SuperSoldier(){
        super();
        addType(Types.RANGED);
    }
}

class Soldier extends Fighter{
    static int id = 0;
    public Types newType(){
        return Types.MELEE;
    }
    public Soldier() {
        super("Soldat" + id);
        image = "http://www.mcmbuzz.com/wp-content/uploads/2014/07/025Pikachu_OS_anime_4.png";
        addType(Types.MELEE);
        id++;
        addMove(new Punch());
        increaseStat(5, Stats.DEFENCE);
        increaseStat(5, Stats.POWER);
        increaseStat(5, Stats.HEALTH);
        increaseStat(5, Stats.EVASION);
    }
}
class Punch extends Attack {
    public Punch(){
        super(Types.MELEE);
        addEffect(new Effect(EffectType.DAMAGE, Move.ABLPOINTS));
    }
    public String description(Fighter f, FighterInfo t){
        return f + " punched " + t +" in the face";
    }
}
